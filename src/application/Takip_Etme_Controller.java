package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class Takip_Etme_Controller {
	
    @FXML
    private TextField takip_et_Textfield;

    private Kullanici mevcut_kullanici;

    public void setTakipEtmeMevcutKullanici(Kullanici kullanici) {
        mevcut_kullanici = kullanici;
    }

    @FXML
    public void takip_et() 
    {
        boolean takip_istegi_gonderildi_mi = false;
        List<Kullanici> kullanicilar = null;
        
        if (mevcut_kullanici == null) {
	        System.err.println("mevcut_kullanici null. setTakipIsteklerimKullanici metodu çağrılmamış olabilir.");
	        return;
	    }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
		    kullanicilar = (List<Kullanici>) ois.readObject();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
        String takip_edilecek_kullanici_string = takip_et_Textfield.getText();
        
      
        for (Kullanici takip_istegi_gonderilicek_kullanici : kullanicilar) {
            
       
            if (takip_edilecek_kullanici_string.equals(takip_istegi_gonderilicek_kullanici.getKullaniciAdi())) {
            	for(String zaten_takip_istegi_gonderilmis_mi : takip_istegi_gonderilicek_kullanici.takip_istekleri) {
                	if(zaten_takip_istegi_gonderilmis_mi.equals(mevcut_kullanici.getKullaniciAdi())) {
                		Alert uyari = new Alert(AlertType.WARNING);
                        uyari.setTitle("Takip İsteği");
                        uyari.setHeaderText(null);
                        uyari.setContentText("Takip isteği zaten gönderilmiş!");
                        uyari.showAndWait();
                        return;                       
                	}
                }
                takip_istegi_gonderilicek_kullanici.takip_istekleri.add(mevcut_kullanici.getKullaniciAdi());
                takip_istegi_gonderildi_mi = true;
                break; 
            }
        }

        if (takip_istegi_gonderildi_mi) {
 
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
                oos.writeObject(kullanicilar);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takip İsteği");
            uyari.setHeaderText(null);
            uyari.setContentText("Takip isteği başarıyla gönderildi!");
            uyari.showAndWait();
        } else {
            Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Takip İsteği");
            uyari.setHeaderText(null);
            uyari.setContentText("Takip isteği gönderilemedi! Böyle bir kullanıcı mevcut değil.");
            uyari.showAndWait();
        }
    }
}
