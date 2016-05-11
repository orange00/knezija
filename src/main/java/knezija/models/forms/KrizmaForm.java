package knezija.models.forms;

import javax.persistence.Lob;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class KrizmaForm extends SynchronizableForm{
	private long id;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String imeOca;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String prezimeOca;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String imeMajke;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String djevojackoPrezimeMajke;

	private String adresaStanovanja;

	/**
	 * DA vs NE
	 */
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String crkvenoVjencani;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String svecenikDolaziNaBlagoslov;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String brojTelefona;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String email;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String imeDjeteta;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String prezimeDjeteta;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String datumRodjenja;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String mjestoRodjenja;

	private String datumKrstenja;

	// treba provest posebnu validaciju da škola i razred budu dobro excel ime
	// za sheet
	@Size(min = 1, max = 25, message = "Ime škole može biti između 1 i 25 znakova. Primjer dobrog imena:'Matija Gubec'. Primjer lošeg imena:'Osnovna škola Matija Gubec'")
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String skola;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String razred;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String zupaKrstenja;
	/**
	 * DA vs NE
	 */
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String pohadjaSkolskiVjeronauk;

	@Lob
	private String krizmanikZahtjevaPosebnuPaznju;

	private String datumPrijave;

	private String datumKrizme;

	private MultipartFile potvrdaOKrstenju;
	private MultipartFile posvjedocenjeZaKumove;

	private boolean isArchive;

	private String isPricest;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getImeMajke() {
		return imeMajke;
	}

	public void setImeMajke(String imeMajke) {
		this.imeMajke = imeMajke;
	}

	public String getDjevojackoPrezimeMajke() {
		return djevojackoPrezimeMajke;
	}

	public void setDjevojackoPrezimeMajke(String djevojackoPrezimeMajke) {
		this.djevojackoPrezimeMajke = djevojackoPrezimeMajke;
	}

	public String getAdresaStanovanja() {
		return adresaStanovanja;
	}

	public void setAdresaStanovanja(String adresaStanovanja) {
		this.adresaStanovanja = adresaStanovanja;
	}

	public String getCrkvenoVjencani() {
		return crkvenoVjencani;
	}

	public void setCrkvenoVjencani(String crkvenoVjencani) {
		this.crkvenoVjencani = crkvenoVjencani;
	}

	public String getSvecenikDolaziNaBlagoslov() {
		return svecenikDolaziNaBlagoslov;
	}

	public void setSvecenikDolaziNaBlagoslov(String svecenikDolaziNaBlagoslov) {
		this.svecenikDolaziNaBlagoslov = svecenikDolaziNaBlagoslov;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(String datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getMjestoRodjenja() {
		return mjestoRodjenja;
	}

	public void setMjestoRodjenja(String mjestoRodjenja) {
		this.mjestoRodjenja = mjestoRodjenja;
	}

	public String getSkola() {
		return skola;
	}

	public void setSkola(String skola) {
		this.skola = skola;
	}

	public String getRazred() {
		return razred;
	}

	public void setRazred(String razred) {
		this.razred = razred;
	}

	public String getZupaKrstenja() {
		return zupaKrstenja;
	}

	public void setZupaKrstenja(String zupaKrstenja) {
		this.zupaKrstenja = zupaKrstenja;
	}

	public String getPohadjaSkolskiVjeronauk() {
		return pohadjaSkolskiVjeronauk;
	}

	public void setPohadjaSkolskiVjeronauk(String pohadjaSkolskiVjeronauk) {
		this.pohadjaSkolskiVjeronauk = pohadjaSkolskiVjeronauk;
	}

	public String getKrizmanikZahtjevaPosebnuPaznju() {
		return krizmanikZahtjevaPosebnuPaznju;
	}

	public void setKrizmanikZahtjevaPosebnuPaznju(
			String krizmanikZahtjevaPosebnuPaznju) {
		this.krizmanikZahtjevaPosebnuPaznju = krizmanikZahtjevaPosebnuPaznju;
	}

	public String getDatumPrijave() {
		return datumPrijave;
	}

	public void setDatumPrijave(String datumPrijave) {
		this.datumPrijave = datumPrijave;
	}

	public MultipartFile getPotvrdaOKrstenju() {
		return potvrdaOKrstenju;
	}

	public void setPotvrdaOKrstenju(MultipartFile potvrdaOKrstenju) {
		this.potvrdaOKrstenju = potvrdaOKrstenju;
	}

	public MultipartFile getPosvjedocenjeZaKumove() {
		return posvjedocenjeZaKumove;
	}

	public void setPosvjedocenjeZaKumove(MultipartFile posvjedocenjeZaKumove) {
		this.posvjedocenjeZaKumove = posvjedocenjeZaKumove;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public String getDatumKrizme() {
		return datumKrizme;
	}

	public void setDatumKrizme(String datumKrizme) {
		this.datumKrizme = datumKrizme;
	}

	public String getIsPricest() {
		return isPricest;
	}

	public void setIsPricest(String isPricest) {
		this.isPricest = isPricest;
	}

	public String getDatumKrstenja() {
		return datumKrstenja;
	}

	public void setDatumKrstenja(String datumKrstenja) {
		this.datumKrstenja = datumKrstenja;
	}
}
