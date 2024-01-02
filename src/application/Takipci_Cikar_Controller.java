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

public class Takipci_Cikar_Controller {
	
	@FXML
    private ListView<String> takipci_cikar_ListView;

    private Kullanici mevcut_kullanici;
	
    private ObservableList<String> takipciler_listesi;
    
	public void setTakipciCikarMevcutKullanici(Kullanici kullanici) {
		
        mevcut_kullanici = kullanici;
        takipciler_listesi = FXCollections.observableArrayList(mevcut_kullanici.takipcilerim);
        takipci_cikar_ListView.setItems(takipciler_listesi);
        
   
		
    }
	
	@FXML
	public void takipci_cikar() {
		
		
		boolean takipci_cikarildi_mi = false;
        List<Kullanici> kullanicilar = null;
        
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
		    kullanicilar = (List<Kullanici>) ois.readObject();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
        
        String takipci_silinecek_kullanici_string = takipci_cikar_ListView.getSelectionModel().getSelectedItem();
        
        for (Kullanici takipci_cikarilacak_kullanici : kullanicilar) {
            
            if (takipci_silinecek_kullanici_string.equals(takipci_cikarilacak_kullanici.getKullaniciAdi())) {
            	mevcut_kullanici.takipcilerim.removeIf( (eleman -> eleman.equals(takipci_silinecek_kullanici_string)) );
                takipci_cikarildi_mi = true;
                break;
            }
        }
        
        
        
        for (Kullanici kullanici : kullanicilar) {
        	if(takipci_silinecek_kullanici_string.equals(kullanici.getKullaniciAdi())) {
        		kullanici.takip_ettiklerim.removeIf(eleman -> eleman.equals(mevcut_kullanici.getKullaniciAdi()));
        	}
        }
        
        // karşı kullanıcının takip ettiklerinden çıkardım dosyaya yazdırıyorum. çünkü aşağıda dosyayı okuma modunda açacağım 
        // öyle  yaparsam yaptığım değişiklikler kaybolur. önce yazmam gerek
        // 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
            oos.writeObject(kullanicilar);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (takipci_cikarildi_mi) {
        	takipciler_listesi = FXCollections.observableArrayList(mevcut_kullanici.takipcilerim);
            takipci_cikar_ListView.setItems(takipciler_listesi);
        
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
     
        			kullanici.takipcilerim = mevcut_kullanici.takipcilerim;
 
        			
        		}
        	}
        	
        	// set ettiğim dosyadaki kullanıcıyı dosyaya yazdır
        	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
                oos.writeObject(kullanicilar);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takipçi Çıkarma");
            uyari.setHeaderText(null);
            uyari.setContentText("Takipçi başarıyla çıkarıldı!!");
            uyari.showAndWait();
        } else {
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takipçi Çıkarma");
            uyari.setHeaderText(null);
            uyari.setContentText("Takipçi çıkarılamadı! Böyle bir kullanıcı mevcut değil.");
            uyari.showAndWait();
        }
        
	}
}
