package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;

public class Takip_Cikar_Controller {
	
	@FXML
    private ListView<String> takip_cikar_ListView;

    private Kullanici mevcut_kullanici;
	
    private ObservableList<String> takip_ettiklerim_listesi;
    
	public void setTakipciCikarMevcutKullanici(Kullanici kullanici) {
		
        mevcut_kullanici = kullanici;
        takip_ettiklerim_listesi = FXCollections.observableArrayList(mevcut_kullanici.takip_ettiklerim);
        takip_cikar_ListView.setItems(takip_ettiklerim_listesi);
        
   
		
    }
	
	@FXML
	public void takip_cikar() {
		
		
		boolean takip_cikarildi_mi = false;
        List<Kullanici> kullanicilar = null;
        
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
		    kullanicilar = (List<Kullanici>) ois.readObject();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
        
        String takip_silinecek_kullanici_string = takip_cikar_ListView.getSelectionModel().getSelectedItem();
        
        for (Kullanici takip_cikarilacak_kullanici : kullanicilar) {
            
            if (takip_silinecek_kullanici_string.equals(takip_cikarilacak_kullanici.getKullaniciAdi())) {

            	mevcut_kullanici.takip_ettiklerim.removeIf( (eleman -> eleman.equals(takip_silinecek_kullanici_string)) );
            	takip_cikarildi_mi = true;
                break;
            }
        }
        
        
        
        for (Kullanici kullanici : kullanicilar) {
        	if(takip_silinecek_kullanici_string.equals(kullanici.getKullaniciAdi())) {
        		kullanici.takipcilerim.removeIf(eleman -> eleman.equals(mevcut_kullanici.getKullaniciAdi()));
        	}
        }
        
        // karşı kullanıcının takibini çıkardım dosyaya yazdırıyorum. çünkü aşağıda dosyayı okuma modunda açacağım öyle yaparsam 
        // yani tekrar okuma moduyla açarsam yaptığım değişiklikler kaybolur.
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
            oos.writeObject(kullanicilar);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (takip_cikarildi_mi) {
        	takip_ettiklerim_listesi = FXCollections.observableArrayList(mevcut_kullanici.takip_ettiklerim);
            takip_cikar_ListView.setItems(takip_ettiklerim_listesi);
        
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
     
        			kullanici.takip_ettiklerim = mevcut_kullanici.takip_ettiklerim;
 
        			
        		}
        	}
        	
        	// set ettiğim dosyadaki kullanıcıyı dosyaya yazdır
        	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
                oos.writeObject(kullanicilar);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takip Çıkarma");
            uyari.setHeaderText(null);
            uyari.setContentText("Takipten başarıyla çıkarıldı!!");
            uyari.showAndWait();
        } else {
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takip Çıkarma");
            uyari.setHeaderText(null);
            uyari.setContentText("Takipten çıkarılamadı! Böyle bir kullanıcı mevcut değil.");
            uyari.showAndWait();
        }
        
	}
}
