package application;


import java.io.Serializable;
import java.util.ArrayList;



class Kullanici implements Serializable , Cloneable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String> takipcilerim = new ArrayList<>();
    ArrayList<String> takip_istekleri = new ArrayList<>();
    ArrayList<String> takip_ettiklerim = new ArrayList<>();

	private String isim;
    private String soyisim;
    private String kullanici_adi;
    private String sifre;

    

	
    
    
    
    public Kullanici(String isim, String soyisim, String kullanici_adi, String sifre) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.kullanici_adi = kullanici_adi;
        this.sifre = sifre;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "İsim: " + isim + ", Soyisim: " + soyisim + ", Kullanıcı Adı: " + kullanici_adi + ", "
        		+ "Şifre : " + sifre + "takipcilerim: " + takipcilerim + "takip_istekleri: " + takip_istekleri + "takip_ettiklerim" + takip_ettiklerim; 
    }
    public String getIsim() {
		return isim;
	}

	public void setIsim(String isim) {
		this.isim = isim;
	}

	public String getSoyisim() {
		return soyisim;
	}

	public void setSoyisim(String soyisim) {
		this.soyisim = soyisim;
	}

	public String getKullaniciAdi() {
		return kullanici_adi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullanici_adi = kullaniciAdi;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}
	
	public ArrayList<String> getTakipcilerim() {
		return takipcilerim;
	}

	public void setTakipcilerim(ArrayList<String> takipcilerim) {
		this.takipcilerim = takipcilerim;
	}

	public ArrayList<String> getTakip_istekleri() {
		return takip_istekleri;
	}

	public void setTakip_istekleri(ArrayList<String> takip_istekleri) {
		this.takip_istekleri = takip_istekleri;
	}
	
}
/* Kullanıcılar: 
 * Emre  Şık     emre123  şık123
 * Enes  Akgül   enes123  akgül123
 * Kadir Yiğit   kadir123 yiğit123
 * Hilmi Özbay   hilmi123 özbay123
 * Eren  Karakuş eren123  karakuş123
 */



