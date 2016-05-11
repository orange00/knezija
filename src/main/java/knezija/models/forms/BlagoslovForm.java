package knezija.models.forms;

import org.hibernate.validator.constraints.NotEmpty;

public class BlagoslovForm extends SynchronizableForm{
	private long id;

	@NotEmpty(message = "Ovo polje je obavezno!")
	private String ime;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String prezime;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String imeUlice;
	private String datum;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String kucniBroj;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String kat;
	private String brojStana;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String vrijemeBlagoslova;
	private String vrijemeBlagoslovaOstalo;
	private String prijePoslijeBozica;
	private String email;
	@NotEmpty(message = "Ovo polje je obavezno!")
	private String brojTelefona;
	private String napomene;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getImeUlice() {
		return imeUlice;
	}

	public void setImeUlice(String imeUlice) {
		this.imeUlice = imeUlice;
	}

	public String getVrijemeBlagoslova() {
		return vrijemeBlagoslova;
	}

	public void setVrijemeBlagoslova(String vrijemeBlagoslova) {
		this.vrijemeBlagoslova = vrijemeBlagoslova;
	}

	public String getPrijePoslijeBozica() {
		return prijePoslijeBozica;
	}

	public void setPrijePoslijeBozica(String prijePoslijeBozica) {
		this.prijePoslijeBozica = prijePoslijeBozica;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public String getNapomene() {
		return napomene;
	}

	public void setNapomene(String napomene) {
		this.napomene = napomene;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getKucniBroj() {
		return kucniBroj;
	}

	public void setKucniBroj(String kucniBroj) {
		this.kucniBroj = kucniBroj;
	}

	public String getKat() {
		return kat;
	}

	public void setKat(String kat) {
		this.kat = kat;
	}

	public String getBrojStana() {
		return brojStana;
	}

	public void setBrojStana(String brojStana) {
		this.brojStana = brojStana;
	}

	public String getVrijemeBlagoslovaOstalo() {
		return vrijemeBlagoslovaOstalo;
	}

	public void setVrijemeBlagoslovaOstalo(String vrijemeBlagoslovaOstalo) {
		this.vrijemeBlagoslovaOstalo = vrijemeBlagoslovaOstalo;
	}
}
