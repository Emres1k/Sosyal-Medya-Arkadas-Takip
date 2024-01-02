package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Hesap_Bilgileri_Controller {
	
	private Kullanici mevcut_kullanici;
	
	@FXML
    private TextField isim_textField;
	
	@FXML
    private TextField soyisim_textField;
	
	@FXML
    private TextField kullanici_adi_textField;
	
	@FXML
    private TextField sifre_textField;
	
	

	public void setHesapBilgilerimKullanici(Kullanici kullanici) {
        this.mevcut_kullanici = kullanici;
        isim_textField.setPromptText(kullanici.getIsim());
        soyisim_textField.setPromptText(kullanici.getSoyisim());
        kullanici_adi_textField.setPromptText(kullanici.getKullaniciAdi());
        sifre_textField.setPromptText(kullanici.getSifre());
    }
	
	@FXML
    public void isim_degistir_buton(ActionEvent event) {
		isim_textField.setDisable(false);

	}
	
	@FXML
    public void soyisim_degistir_buton(ActionEvent event) {
		soyisim_textField.setDisable(false);
	}
	
	@FXML
    public void kullanici_adi_degistir_buton(ActionEvent event) {
		kullanici_adi_textField.setDisable(false);
	}
	
	@FXML
    public void sifre_degistir_buton(ActionEvent event) {
		sifre_textField.setDisable(false);
	}
	
	@FXML
    public void onayla_buton(ActionEvent event) {
		String eski_kullanici_adi = null;        // kullanı adımız değişirse önlem alıyoruz.       
		String isim_text = isim_textField.getText();
		String soyisim_text = soyisim_textField.getText();
		String kullanici_adi_text = kullanici_adi_textField.getText();
		String sifre_text = sifre_textField.getText();
		if (isim_text.isEmpty()) {
            
        } else {
            mevcut_kullanici.setIsim(isim_text);
        }
		if (soyisim_text.isEmpty()) {
            
        } else {
            mevcut_kullanici.setSoyisim(soyisim_text);;
        }
		if (kullanici_adi_text.isEmpty()) {
            
        } else {
        	eski_kullanici_adi = mevcut_kullanici.getKullaniciAdi();
            mevcut_kullanici.setKullaniciAdi(kullanici_adi_text);
        }
		if (sifre_text.isEmpty()) {
            
        } else {
            mevcut_kullanici.setSifre(sifre_text);
        }
		
		// dosyayı oku (dosyadaki kullanılara erişmek için)
		List<Kullanici> kullanicilar = null;
    	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
		    kullanicilar = (List<Kullanici>) ois.readObject();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
    	
    	// mevcut kullanıcıyı dosyaya set et
    	
    
    	for (int i = 0; i < kullanicilar.size(); i++) {
    	    Kullanici kullanici = kullanicilar.get(i);

    	    if (eski_kullanici_adi != null && kullanici.getKullaniciAdi().equals(eski_kullanici_adi)) {
    	       
    	        try {
    	            kullanicilar.set(i, (Kullanici) mevcut_kullanici.clone());

    	        } catch (CloneNotSupportedException e) {
    	            e.printStackTrace();
    	        }
    	    } 
    	    else if (eski_kullanici_adi == null && kullanici.getKullaniciAdi().equals(mevcut_kullanici.getKullaniciAdi())) {
    	        try {
    	        	System.out.println(kullanicilar);
    	            kullanicilar.set(i, (Kullanici) mevcut_kullanici.clone());
    	            System.out.println(kullanicilar);
    	            // bu şekilde indis belirtmemiz gerekiyor yoksa döngüden çıktıtkan sonra eski haline döner.
    	        } catch (CloneNotSupportedException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	}
    	
    	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
            oos.writeObject(kullanicilar);
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
		
		Alert uyari = new Alert(AlertType.INFORMATION);
        uyari.setTitle("Kullanıcı Bilgileri Güncelleme");
        uyari.setHeaderText(null);
        uyari.setContentText("Kullanıcı bilgileri başarıyla güncellendi!");
        uyari.showAndWait();

     
	}
}
