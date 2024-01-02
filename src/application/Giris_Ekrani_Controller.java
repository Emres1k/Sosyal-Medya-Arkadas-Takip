package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Giris_Ekrani_Controller {
	
    @FXML
    private TextField kullanici_adi_field;

    @FXML
    private PasswordField sifre_field;

 
    
    @FXML
    private Button giris_yap_butonu;
    
    @FXML
    private Button kayit_ol_butonu;



    
    
    @FXML
    private void kayit_ol() throws IOException {
    	
        
    	Kayit_Controller kayit_controller = new Kayit_Controller();
    	kayit_controller.set_kayit_ol();
       
    }
    
    @FXML
    private void giris_yap() throws FileNotFoundException, IOException, ClassNotFoundException {
    	
        String username = kullanici_adi_field.getText();
        String password = sifre_field.getText();
        boolean giris_yapildi_mi=false; 
       
        List<Kullanici> kullanicilar;
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
        	kullanicilar = (List<Kullanici>) ois.readObject();
        }       
         
        for (Kullanici mevcut_kullanici : kullanicilar) {
        	
            if (mevcut_kullanici.getKullaniciAdi().equals(username) && mevcut_kullanici.getSifre().equals(password)) {
            	giris_yapildi_mi=true;
            	kullanici_ekranina_giris(mevcut_kullanici);
                return;
            }
        }
        if (!(giris_yapildi_mi)) {
        	Alert uyari = new Alert(AlertType.WARNING);
            uyari.setTitle("Uyarı");
            uyari.setHeaderText(null);
            uyari.setContentText("Kullanıcı girişi başarısız!");

            uyari.showAndWait();
        }
        
        
    }

    private void kullanici_ekranina_giris(Kullanici kullanici) {
        try {
        	
        	Alert uyari = new Alert(AlertType.INFORMATION);
            uyari.setTitle("Giriş");
            uyari.setHeaderText(null);
            uyari.setContentText("Başarıyla giriş yapıldı!");
            uyari.showAndWait();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Kullanici_Ekrani.fxml"));
            Parent root = loader.load();

           
            Kullanici_Controller controller = loader.getController();

           
            controller.setMevcutKullanici(kullanici);

            Stage primaryStage = (Stage) kullanici_adi_field.getScene().getWindow();
            Image ikon = new Image("instagram.png");
            primaryStage.getIcons().add(ikon);
            
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
