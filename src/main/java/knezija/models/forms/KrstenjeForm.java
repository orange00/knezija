package knezija.models.forms;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class KrstenjeForm {
	private long id;
	
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String imeOca;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String prezimeOca;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String vjeraOca;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String narodnostOca;
	private String zanimanjeOca;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String imeMajke;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String prezimeMajke;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String vjeraMajke;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String narodnostMajke;
	private String zanimanjeMajke;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String datumCrkvenogVjencanja;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String zupaCrkvenogVjencanja;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String mjesto;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String postanskiBroj;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String ulica;
	private String kucniBroj;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String brojTelefona;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String imeDjeteta;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String prezimeDjeteta;
	@Size(min=11, max=11, message="OIB mora imati {min} znamenki!")
	private String OIBDjeteta;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String spol;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String datumRodjenja;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String mjestoRodjenja;
	private String redniBrojDjeteta;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String imeKuma1;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String prezimeKuma1;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String zupaKuma1;

	private String imeKuma2;
	private String prezimeKuma2;
	private String zupaKuma2;

	private MultipartFile izvodMaticnaKnjiga;
	private MultipartFile posvjedocenjeZaKumove;
	private MultipartFile suglasnostNasegZupnika;

	private String datumKrstenja;
	
	private String datumPrijave;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String zakonitoIliCivilno;

	@Email
	private String email;

	public String getImeOca() {
		return imeOca;
	}

	public void setImeOca(String imeOca) {
		this.imeOca = imeOca;
	}

	public String getPrezimeOca() {
		return prezimeOca;
	}

	public void setPrezimeOca(String prezimeOca) {
		this.prezimeOca = prezimeOca;
	}

	public String getVjeraOca() {
		return vjeraOca;
	}

	public void setVjeraOca(String vjeraOca) {
		this.vjeraOca = vjeraOca;
	}

	public String getNarodnostOca() {
		return narodnostOca;
	}

	public void setNarodnostOca(String narodnostOca) {
		this.narodnostOca = narodnostOca;
	}

	public String getZanimanjeOca() {
		return zanimanjeOca;
	}

	public void setZanimanjeOca(String zanimanjeOca) {
		this.zanimanjeOca = zanimanjeOca;
	}

	public String getImeMajke() {
		return imeMajke;
	}

	public void setImeMajke(String imeMajke) {
		this.imeMajke = imeMajke;
	}

	public String getPrezimeMajke() {
		return prezimeMajke;
	}

	public void setPrezimeMajke(String prezimeMajke) {
		this.prezimeMajke = prezimeMajke;
	}

	public String getVjeraMajke() {
		return vjeraMajke;
	}

	public void setVjeraMajke(String vjeraMajke) {
		this.vjeraMajke = vjeraMajke;
	}

	public String getNarodnostMajke() {
		return narodnostMajke;
	}

	public void setNarodnostMajke(String narodnostMajke) {
		this.narodnostMajke = narodnostMajke;
	}

	public String getZanimanjeMajke() {
		return zanimanjeMajke;
	}

	public void setZanimanjeMajke(String zanimanjeMajke) {
		this.zanimanjeMajke = zanimanjeMajke;
	}

	public String getDatumCrkvenogVjencanja() {
		return datumCrkvenogVjencanja;
	}

	public void setDatumCrkvenogVjencanja(String datumCrkvenogVjencanja) {
		this.datumCrkvenogVjencanja = datumCrkvenogVjencanja;
	}

	public String getZupaCrkvenogVjencanja() {
		return zupaCrkvenogVjencanja;
	}

	public void setZupaCrkvenogVjencanja(String zupaCrkvenogVjencanja) {
		this.zupaCrkvenogVjencanja = zupaCrkvenogVjencanja;
	}

	public String getMjesto() {
		return mjesto;
	}

	public void setMjesto(String mjesto) {
		this.mjesto = mjesto;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public String getImeDjeteta() {
		return imeDjeteta;
	}

	public void setImeDjeteta(String imeDjeteta) {
		this.imeDjeteta = imeDjeteta;
	}

	public String getPrezimeDjeteta() {
		return prezimeDjeteta;
	}

	public void setPrezimeDjeteta(String prezimeDjeteta) {
		this.prezimeDjeteta = prezimeDjeteta;
	}

	public String getOIBDjeteta() {
		return OIBDjeteta;
	}

	public void setOIBDjeteta(String oIBDjeteta) {
		OIBDjeteta = oIBDjeteta;
	}

	public String getMjestoRodjenja() {
		return mjestoRodjenja;
	}

	public void setMjestoRodjenja(String mjestoRodjenja) {
		this.mjestoRodjenja = mjestoRodjenja;
	}

	public String getRedniBrojDjeteta() {
		return redniBrojDjeteta;
	}

	public void setRedniBrojDjeteta(String redniBrojDjeteta) {
		this.redniBrojDjeteta = redniBrojDjeteta;
	}

	public String getImeKuma1() {
		return imeKuma1;
	}

	public void setImeKuma1(String imeKuma1) {
		this.imeKuma1 = imeKuma1;
	}

	public String getPrezimeKuma1() {
		return prezimeKuma1;
	}

	public void setPrezimeKuma1(String prezimeKuma1) {
		this.prezimeKuma1 = prezimeKuma1;
	}

	public String getImeKuma2() {
		return imeKuma2;
	}

	public void setImeKuma2(String imeKuma2) {
		this.imeKuma2 = imeKuma2;
	}

	public String getPrezimeKuma2() {
		return prezimeKuma2;
	}

	public void setPrezimeKuma2(String prezimeKuma2) {
		this.prezimeKuma2 = prezimeKuma2;
	}

	public MultipartFile getIzvodMaticnaKnjiga() {
		return izvodMaticnaKnjiga;
	}

	public void setIzvodMaticnaKnjiga(MultipartFile izvodMaticnaKnjiga) {
		this.izvodMaticnaKnjiga = izvodMaticnaKnjiga;
	}

	public MultipartFile getPosvjedocenjeZaKumove() {
		return posvjedocenjeZaKumove;
	}

	public void setPosvjedocenjeZaKumove(MultipartFile posvjedocenjeZaKumove) {
		this.posvjedocenjeZaKumove = posvjedocenjeZaKumove;
	}

	public MultipartFile getSuglasnostNasegZupnika() {
		return suglasnostNasegZupnika;
	}

	public void setSuglasnostNasegZupnika(MultipartFile suglasnostNasegZupnika) {
		this.suglasnostNasegZupnika = suglasnostNasegZupnika;
	}

	public String getZakonitoIliCivilno() {
		return zakonitoIliCivilno;
	}

	public void setZakonitoIliCivilno(String zakonitoIliCivilno) {
		this.zakonitoIliCivilno = zakonitoIliCivilno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(String datumRođenja) {
		this.datumRodjenja = datumRođenja;
	}

	public String getDatumKrstenja() {
		return datumKrstenja;
	}

	public void setDatumKrstenja(String datumKrstenja) {
		this.datumKrstenja = datumKrstenja;
	}

	public String getSpol() {
		return spol;
	}

	public void setSpol(String spol) {
		this.spol = spol;
	}

	public String getZupaKuma1() {
		return zupaKuma1;
	}

	public void setZupaKuma1(String zupaKuma1) {
		this.zupaKuma1 = zupaKuma1;
	}

	public String getZupaKuma2() {
		return zupaKuma2;
	}

	public void setZupaKuma2(String zupaKuma2) {
		this.zupaKuma2 = zupaKuma2;
	}

	public String getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}

	public String getKucniBroj() {
		return kucniBroj;
	}

	public void setKucniBroj(String kucniBroj) {
		this.kucniBroj = kucniBroj;
	}

	public String getDatumPrijave() {
		return datumPrijave;
	}

	public void setDatumPrijave(String datumPrijave) {
		this.datumPrijave = datumPrijave;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
