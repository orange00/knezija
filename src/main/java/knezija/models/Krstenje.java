package knezija.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Krstenje {
	@GeneratedValue
	@Id
	private long id;
	
	@Column
	private String imeOca;
	@Column
	private String prezimeOca;
	@Column
	private String vjeraOca;
	@Column
	private String narodnostOca;
	@Column
	private String zanimanjeOca;
	
	@Column
	private String imeMajke;
	@Column
	private String prezimeMajke;
	@Column
	private String vjeraMajke;
	@Column
	private String narodnostMajke;
	@Column
	private String zanimanjeMajke;
	
	@Column
	private Date datumCrkvenogVjencanja;
	@Column
	private String zupaCrkvenogVjencanja;
	
	@Column
	private String mjesto;
	@Column
	private long postanskiBroj;
	@Column
	private String ulica;
	@Column
	private long kucniBroj;
	@Column
	private String brojTelefona;
	
	@Column
	private String imeDjeteta;
	@Column
	private String prezimeDjeteta;
	@Column
	private String OIBDjeteta;
	@Column
	private String spol;
	
	@Column
	private Date datumRodjenja;
	@Column
	private String mjestoRodjenja;
	@Column
	private String redniBrojDjeteta;
	
	@Column
	private String imeKuma1;
	@Column
	private String prezimeKuma1;
	@Column
	private String zupaKuma1;
	
	@Column
	private String imeKuma2;
	@Column
	private String prezimeKuma2;
	@Column
	private String zupaKuma2;
	
	@Column
	private byte[] izvodMaticnaKnjiga;
	@Column
	private byte[] posvjedocenjeZaKumove;
	@Column
	private byte[] suglasnostNasegZupnika;
	
	@Column
	private Date datumPrijave;
	
	@Column
	private Date datumKrstenja;
	
	@Column
	private String zakonitoIliCivilno;
	
	@Column
	private String email;

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

	public Date getDatumCrkvenogVjencanja() {
		return datumCrkvenogVjencanja;
	}

	public void setDatumCrkvenogVjencanja(Date datumCrkvenogVjencanja) {
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

	public long getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(long postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public long getKucniBroj() {
		return kucniBroj;
	}

	public void setKucniBroj(long kucniBroj) {
		this.kucniBroj = kucniBroj;
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

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
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

	public byte[] getIzvodMaticnaKnjiga() {
		return izvodMaticnaKnjiga;
	}

	public void setIzvodMaticnaKnjiga(byte[] izvodMaticnaKnjiga) {
		this.izvodMaticnaKnjiga = izvodMaticnaKnjiga;
	}

	public byte[] getPosvjedocenjeZaKumove() {
		return posvjedocenjeZaKumove;
	}

	public void setPosvjedocenjeZaKumove(byte[] posvjedocenjeZaKumove) {
		this.posvjedocenjeZaKumove = posvjedocenjeZaKumove;
	}

	public byte[] getSuglasnostNasegZupnika() {
		return suglasnostNasegZupnika;
	}

	public void setSuglasnostNasegZupnika(byte[] suglasnostNasegZupnika) {
		this.suglasnostNasegZupnika = suglasnostNasegZupnika;
	}

	public Date getDatumPrijave() {
		return datumPrijave;
	}

	public void setDatumPrijave(Date datumPrijave) {
		this.datumPrijave = datumPrijave;
	}

	public Date getDatumKrstenja() {
		return datumKrstenja;
	}

	public void setDatumKrstenja(Date datumKrstenja) {
		this.datumKrstenja = datumKrstenja;
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
}
