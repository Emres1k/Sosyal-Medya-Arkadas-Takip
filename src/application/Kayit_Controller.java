package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Kayit_Controller {
	@FXML
    private TextField isim_field;
	
	@FXML
    private TextField soyisim_field;
	
	@FXML
    private TextField kullanici_adi_kayit_field;

    @FXML
    private PasswordField sifre_kayit_field;
	
    @FXML
    private Button kayit_ol_buton;
    
    
    List<Kullanici> kullanicilar = new ArrayList<>();

    public void set_kayit_ol() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Kayit_Ekrani.fxml"));
    	Parent root = loader.load();
    	
    	Stage stage = new Stage();
        stage.setTitle("Kayıt Olma Ekranı");
        
    	Image ikon = new Image("instagram.png");
    	stage.getIcons().add(ikon);
        
      
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
	@FXML
	public void kayit_ol(){
		String kullanici_adi = kullanici_adi_kayit_field.getText();
        String sifre = sifre_kayit_field.getText();
        String isim = isim_field.getText();
        String soyisim = soyisim_field.getText();
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("kullanicilar.dat"))) {
		    kullanicilar = (List<Kullanici>) ois.readObject();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
        
        Kullanici yeni_kullanici = new Kullanici(isim,soyisim,kullanici_adi,sifre);
        kullanicilar.add(yeni_kullanici);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kullanicilar.dat"))) {
            oos.writeObject(kullanicilar);
        } catch (IOException e) {
            e.printStackTrace();
        }
  
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Kullanıcı Kaydı Başarılı");
        alert.setHeaderText(null);
        alert.setContentText("Kullanıcı Başarıyla Oluşturuldu");

       
        ButtonType onayButton = new ButtonType("Onay", ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(onayButton);

       
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == onayButton) {
        	kullanici_adi_kayit_field.clear();
        	sifre_kayit_field.clear();
        	isim_field.clear();
        	soyisim_field.clear();
        }
	}
}
