package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Kullanici_Controller {
	
	@FXML
	private ListView<String> takipcilerim_ekrani;
	
	@FXML
	private ListView<String> takip_ettiklerim_ekrani;

	@FXML
    private Label hosgeldiniz_label;
	
	
	
	
    private Kullanici mevcut_kullanici;

    public void setMevcutKullanici(Kullanici kullanici) {
        this.mevcut_kullanici = kullanici;
        initializeAfterSetKullanici();
    }

    private void initializeAfterSetKullanici() {
    	
    	takip_ettiklerim_alanini_guncelle();
        takipcilerim_ekrani_guncelle();
        hosgeldiniz_label_guncelle();
    }
    
    
    public void takip_ettiklerim_alanini_guncelle() {
    	List<Kullanici> kullanicilar = null;
    	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
        	kullanicilar = (List<Kullanici>) ois.readObject();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
    	// Kullanicilar listesi null degilse ve boş değilse işleme devam et
    	ArrayList<String> takip_ettiklerim = new ArrayList<>();
    	
        for (Kullanici kullanici : kullanicilar) {
        	for (String benim_takipcim : mevcut_kullanici.takip_ettiklerim) {
        		if(benim_takipcim.equals(kullanici.getKullaniciAdi())) {
        			takip_ettiklerim.add(kullanici.getKullaniciAdi());
        		}
        	}     	
        } 
        takip_ettiklerim_ekrani.getItems().setAll(takip_ettiklerim);
    }
    
    public void takipcilerim_ekrani_guncelle() {
    	List<Kullanici> kullanicilar = null;
    	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
        	kullanicilar = (List<Kullanici>) ois.readObject();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
    	// Kullanicilar listesi null degilse ve boş değilse işleme devam et
    	ArrayList<String> takipcilerim = new ArrayList<>();
    	
        for (Kullanici kullanici : kullanicilar) {
        	for (String benim_takip_ettigim : mevcut_kullanici.takipcilerim) {
        		if(benim_takip_ettigim.equals(kullanici.getKullaniciAdi())) {
        			takipcilerim.add(kullanici.getKullaniciAdi());
        		}
        	}     	
        } 
        takipcilerim_ekrani.getItems().setAll(takipcilerim);
    }
    
    
    @FXML
    public void takip_et()  {
    	
    	
    	
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Takip_Etme_Ekrani.fxml"));
            Parent root = loader.load();
            
            Takip_Etme_Controller takip_Etme_Controller= loader.getController();
            takip_Etme_Controller.setTakipEtmeMevcutKullanici(mevcut_kullanici);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Takip Etme Ekranı");
            stage.setScene(scene);
            stage.show();
     
            
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    
   
    
    @FXML
    public void takip_cikar() {
    	
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Takip_Cikar.fxml"));
            Parent root = loader.load();

            Takip_Cikar_Controller takip_Cikar_Controller = loader.getController();
            takip_Cikar_Controller.setTakipciCikarMevcutKullanici(mevcut_kullanici);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Takip Çıkarma Ekranı");
            stage.setScene(scene);

            // Stage'in X tuşuna basıldığında çalışacak olayı tanımlayın
            stage.setOnCloseRequest(event -> {
            	takipcilerim_ekrani_guncelle();
            });

            stage.show();
            
            stage.setOnCloseRequest(event -> {
            	takipcilerim_ekrani_guncelle();
                takip_ettiklerim_alanini_guncelle();
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void takipci_cikar() {
    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Takipci_Cikarma.fxml"));
            Parent root = loader.load();

            Takipci_Cikar_Controller takipci_Cikar_Controller = loader.getController();
            takipci_Cikar_Controller.setTakipciCikarMevcutKullanici(mevcut_kullanici);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Takipçi Çıkarma Ekranı");
            stage.setScene(scene);

            // Stage'in X tuşuna basıldığında çalışacak olayı tanımlayın
            stage.setOnCloseRequest(event -> {
            	takipcilerim_ekrani_guncelle();
            });

            stage.show();
            
            stage.setOnCloseRequest(event -> {
            	takipcilerim_ekrani_guncelle();
                takip_ettiklerim_alanini_guncelle();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void takip_istekleri() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Takip_Istekleri.fxml"));
            Parent root = loader.load(); // Burada oluşturulan Parent nesnesini kontrol edin
            
            // Controller'ı alın ve kullanıcıyı ayarlayın
            Takip_Istekleri_Controller takip_Istekleri_Controller = loader.getController();
            takip_Istekleri_Controller.setTakipIsteklerimKullanici(mevcut_kullanici);

            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Takip İstekleri Ekranı");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event -> {
            	takipcilerim_ekrani_guncelle();
                takip_ettiklerim_alanini_guncelle();
            });
         
        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
       
    }
   
    
    @FXML
    public void hesap_bilgileri() {
    	System.out.println(mevcut_kullanici);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Hesap_Bilgileri.fxml"));
            Parent root = loader.load(); // Burada oluşturulan Parent nesnesini kontrol edin
            
            // Controller'ı alın ve kullanıcıyı ayarlayın
            Hesap_Bilgileri_Controller hesap_bilgileri_Controller = loader.getController();
            hesap_bilgileri_Controller.setHesapBilgilerimKullanici(mevcut_kullanici);

            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Takip Etme Ekranı");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event -> {
                hosgeldiniz_label_guncelle();
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hosgeldiniz_label_guncelle() {
        List<Kullanici> kullanicilar = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
            kullanicilar = (List<Kullanici>) ois.readObject();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String hosgeldiniz_isim = null ;
        String hosgeldiniz_soyisim = null;
        for (Kullanici kullanici : kullanicilar) {
            if(kullanici.getKullaniciAdi().equals(mevcut_kullanici.getKullaniciAdi())) {
            	
                hosgeldiniz_isim = kullanici.getIsim();
                hosgeldiniz_soyisim = kullanici.getSoyisim();
            }
        }
        
        // adımız değişirse hoş geldiniz yazısı düzelsin.
        hosgeldiniz_label.setText("Hoşgeldiniz " + hosgeldiniz_isim + " " + hosgeldiniz_soyisim);
    }
    @FXML
    public void cikis() {
    	Alert uyari = new Alert(AlertType.INFORMATION);
        uyari.setTitle("Çıkış");
        uyari.setHeaderText(null);
        uyari.setContentText("Başarıyla çıkış yapıldı!");
        uyari.showAndWait();
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Giris_Ekrani.fxml"));
            Parent root = loader.load();

            // Kullanici_Controller'ı al
            Giris_Ekrani_Controller controller = loader.getController();

            
            // Sahneyi değiştir
            Stage primaryStage = (Stage) hosgeldiniz_label.getScene().getWindow();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
    

