package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Takip_Istekleri_Controller {
	
	private Kullanici mevcut_kullanici;
	
	@FXML
	private ListView<String> takip_istekleri_ListView;
	
	@FXML
    private Button onayla_buton;

    @FXML
    private Button sil_buton;
	 
    @FXML
    private Label durum_kontrolu;
    
    private ObservableList<String> takip_istekleri_listesi;
    
	public void setTakipIsteklerimKullanici(Kullanici kullanici) {
        this.mevcut_kullanici = kullanici;
        baslat();
    }
	
	
    public void baslat() {
		if (mevcut_kullanici == null) {
	        System.err.println("mevcut_kullanici null. setTakipIsteklerimKullanici metodu çağrılmamış olabilir.");
	        return;
	    }
		if (takip_istekleri_ListView != null && durum_kontrolu != null) {
			takip_istekleri_listesi = FXCollections.observableArrayList(mevcut_kullanici.takip_istekleri);
		    takip_istekleri_ListView.setItems(takip_istekleri_listesi);
		    durum_kontrolu.setText("Lütfen bir takip isteği seçin");
		} 
		else {
			System.err.println("takip_istekleri_ListView veya durum_kontrolu null. FXML dosyasını kontrol edin.");
		}
    }
	
	
	@FXML
    public void onayla_buton(ActionEvent event) {
		
		List<Kullanici> kullanicilar = null;
        
		boolean takipci_onaylandi_mi = false;
        
		
        String takip_istegi_onaylanacak_kullanici_string = takip_istekleri_ListView.getSelectionModel().getSelectedItem();
   
        List<String> tempTakipIstekleri = new ArrayList<>(mevcut_kullanici.takip_istekleri);
        // gecici bir arraylist oluşturdum çünkü mevcut_kullanici.takip_istekleri içeride değişecek çok önemli burası
        for (String takip_isteklerimde_var_mi : tempTakipIstekleri) {
        	if(takip_istegi_onaylanacak_kullanici_string.equals(takip_isteklerimde_var_mi)){
        		mevcut_kullanici.takipcilerim.add(takip_istegi_onaylanacak_kullanici_string);
        		mevcut_kullanici.takip_istekleri.removeIf(eleman -> eleman.equals(takip_istegi_onaylanacak_kullanici_string));
        		
        		takipci_onaylandi_mi = true;
        	}
        }
        
        // takip isteği onayladık karşı kullanıcının takip ettiklerini güncelle
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
		    kullanicilar = (List<Kullanici>) ois.readObject();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
        
        for (Kullanici kullanici : kullanicilar) {
        	if(takip_istegi_onaylanacak_kullanici_string.equals(kullanici.getKullaniciAdi())) {
        		kullanici.takip_ettiklerim.add(mevcut_kullanici.getKullaniciAdi());
        	}
        }
        
        // karşı kullanıcının takip etme durumunu dosyaya yaz
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
            oos.writeObject(kullanicilar);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (takipci_onaylandi_mi) {
        	
        	takip_istekleri_listesi.setAll(mevcut_kullanici.takip_istekleri);
        	
        	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
    		    kullanicilar = (List<Kullanici>) ois.readObject();
    		} catch (FileNotFoundException e1) {
    		    e1.printStackTrace();
    		} catch (IOException | ClassNotFoundException e) {
    		    e.printStackTrace();
    		}
        	
        	// mevcut kullanıcıyı dosyaya set et
           
        	for (Kullanici kullanici : kullanicilar) {
        		if(kullanici.getKullaniciAdi().equals(mevcut_kullanici.getKullaniciAdi())) {
        			kullanici.takipcilerim = mevcut_kullanici.takipcilerim;
        			kullanici.takip_istekleri = mevcut_kullanici.takip_istekleri;
        			
        		}
        	}
        	
        	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
                oos.writeObject(kullanicilar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        	
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takip İsteği");
            uyari.setHeaderText(null);
            uyari.setContentText("Takip isteği başarıyla onaylandı!");
            uyari.showAndWait();
        } else {
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takip İsteği");
            uyari.setHeaderText(null);
            uyari.setContentText("Takip isteği onaylanamadı! Böyle bir kullanıcı mevcut değil.");
            uyari.showAndWait();
        }
    }

    @FXML
    public void sil_buton(ActionEvent event) {
    	List<Kullanici> kullanicilar = null;
        
    	boolean takipci_silindi_mi = false;
    	
    	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
    	    kullanicilar = (List<Kullanici>) ois.readObject();
    	} catch (FileNotFoundException e1) {
    	    e1.printStackTrace();
    	} catch (IOException | ClassNotFoundException e) {
    	    e.printStackTrace();
    	}
        
        String takip_istegi_silinecek_kullanici_string = takip_istekleri_ListView.getSelectionModel().getSelectedItem();
        
        List<String> tempTakipIstekleri = new ArrayList<>(mevcut_kullanici.takip_istekleri);
        
        for (String takip_isteklerimde_var_mi : tempTakipIstekleri) {
        	if(takip_istegi_silinecek_kullanici_string.equals(takip_isteklerimde_var_mi)){
        		mevcut_kullanici.takip_istekleri.removeIf(eleman -> eleman.equals(takip_istegi_silinecek_kullanici_string));
        		takipci_silindi_mi = true;
        	}
        }
        
        for (Kullanici kullanici : kullanicilar) {
    		String kullanici_id = kullanici.getKullaniciAdi();
    		String mevcut_kullanici_id = mevcut_kullanici.getKullaniciAdi();
    		if(kullanici_id.equals(mevcut_kullanici_id)) {
    			kullanici.takip_istekleri = mevcut_kullanici.takip_istekleri;
    			
    		}
    	}
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
            oos.writeObject(kullanicilar);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (takipci_silindi_mi) {
        	takip_istekleri_listesi.setAll(mevcut_kullanici.takip_istekleri);
        	
        	// dosyayı oku (dosyadaki kullanılara erişmek için)
        	
        	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
    		    kullanicilar = (List<Kullanici>) ois.readObject();
    		} catch (FileNotFoundException e1) {
    		    e1.printStackTrace();
    		} catch (IOException | ClassNotFoundException e) {
    		    e.printStackTrace();
    		}
        	
        	// mevcut kullanıcıyı dosyaya set et
           
        	for (Kullanici kullanici : kullanicilar) {
        		String kullanici_id = kullanici.getKullaniciAdi();
        		String mevcut_kullanici_id = mevcut_kullanici.getKullaniciAdi();
        		if(kullanici_id.equals(mevcut_kullanici_id)) {
        			kullanici.takip_istekleri = mevcut_kullanici.takip_istekleri;
        			
        		}
        	}
        	
        	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
                oos.writeObject(kullanicilar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        	
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takip İsteği");
            uyari.setHeaderText(null);
            uyari.setContentText("Takip isteği başarıyla silindi!");
            uyari.showAndWait();
        } else {
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takip İsteği");
            uyari.setHeaderText(null);
            uyari.setContentText("Takip isteği silinemedi!");
            uyari.showAndWait();
        }
    }
}
