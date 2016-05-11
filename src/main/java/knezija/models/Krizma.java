package knezija.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.joda.time.DateTimeComparator;

@Entity
public class Krizma implements Prijava, Record<Krizma>{
	@GeneratedValue
	@Id
	private long id;
	
	@Column
	private String imeOca;
	@Column
	private String prezimeOca;
	
	@Column
	private String imeMajke;
	@Column
	private String djevojackoPrezimeMajke;
	
	@Column
	private String adresaStanovanja;
	
	/**
	 * DA vs NE
	 */
	@Column
	private String crkvenoVjencani;
	@Column
	private String svecenikDolaziNaBlagoslov;
	
	@Column
	private String brojTelefona;
	@Column
	private String email;
	
	@Column
	private String imeDjeteta;
	@Column
	private String prezimeDjeteta;
	@Column
	private Date datumRodjenja;
	@Column
	private String mjestoRodjenja;

	@Column
	private Date datumKrstenja;
	
	@Column
	private String skola;
	@Column
	private String razred;
	
	@Column
	private String zupaKrstenja;
	/**
	 * DA vs NE
	 */
	@Column
	private String pohadjaSkolskiVjeronauk;
	
	@Column
	private String krizmanikZahtjevaPosebnuPaznju;
	
	@Column
	private Date datumPrijave;
	
	@Column
	private Date datumKrizme;
	
	@Lob
	@Column
	private byte[] potvrdaOKrstenju;
	@Lob
	@Column
	private byte[] posvjedocenjeZaKumove;
	
	@Column
	private boolean isArchive;
	
	/**
	 * Umjesto isPricest bi bolje bilo koristit nasljedjivanje, tj., 
	 * nasljedit krizmu i to je pričest. Time bi imali novu tablicu, 
	 * ali, persistiranje bi bilo jednostavno.
	 */
	private boolean isPricest;

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

	public Date getDatumPrijave() {
		return datumPrijave;
	}

	public void setDatumPrijave(Date datumPrijave) {
		this.datumPrijave = datumPrijave;
	}

	public byte[] getPotvrdaOKrstenju() {
		return potvrdaOKrstenju;
	}

	public void setPotvrdaOKrstenju(byte[] potvrdaOKrstenju) {
		this.potvrdaOKrstenju = potvrdaOKrstenju;
	}

	public byte[] getPosvjedocenjeZaKumove() {
		return posvjedocenjeZaKumove;
	}

	public void setPosvjedocenjeZaKumove(byte[] posvjedocenjeZaKumove) {
		this.posvjedocenjeZaKumove = posvjedocenjeZaKumove;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public Date getDatumKrizme() {
		return datumKrizme;
	}

	public void setDatumKrizme(Date datumKrizme) {
		this.datumKrizme = datumKrizme;
	}

	public boolean isPricest() {
		return isPricest;
	}

	public void setPricest(boolean isPricest) {
		this.isPricest = isPricest;
	}

	public Date getDatumKrstenja() {
		return datumKrstenja;
	}

	public void setDatumKrstenja(Date datumKrstenja) {
		this.datumKrstenja = datumKrstenja;
	}

	@Override
	public boolean logicallyEquals(Krizma obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Krizma other = (Krizma) obj;
		if (adresaStanovanja == null) {
			if (other.adresaStanovanja != null)
				return false;
		} else if (!adresaStanovanja.equals(other.adresaStanovanja))
			return false;
		if (brojTelefona == null) {
			if (other.brojTelefona != null)
				return false;
		} else if (!brojTelefona.equals(other.brojTelefona))
			return false;
		if (crkvenoVjencani == null) {
			if (other.crkvenoVjencani != null)
				return false;
		} else if (!crkvenoVjencani.equals(other.crkvenoVjencani))
			return false;
		if (datumKrizme == null) {
			if (other.datumKrizme != null)
				return false;
		} else if (DateTimeComparator.getDateOnlyInstance().compare(datumKrizme, other.datumKrizme)!=0)
			return false;
		if (datumKrstenja == null) {
			if (other.datumKrstenja != null)
				return false;
		} else if (DateTimeComparator.getDateOnlyInstance().compare(datumKrstenja, other.datumKrstenja)!=0)
			return false;
		if (datumPrijave == null) {
			if (other.datumPrijave != null)
				return false;
		} else if (DateTimeComparator.getDateOnlyInstance().compare(datumPrijave, other.datumPrijave)!=0)
			return false;
		if (datumRodjenja == null) {
			if (other.datumRodjenja != null)
				return false;
		} else if (DateTimeComparator.getDateOnlyInstance().compare(datumRodjenja, other.datumRodjenja)!=0)
			return false;
		if (djevojackoPrezimeMajke == null) {
			if (other.djevojackoPrezimeMajke != null)
				return false;
		} else if (!djevojackoPrezimeMajke.equals(other.djevojackoPrezimeMajke))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (imeDjeteta == null) {
			if (other.imeDjeteta != null)
				return false;
		} else if (!imeDjeteta.equals(other.imeDjeteta))
			return false;
		if (imeMajke == null) {
			if (other.imeMajke != null)
				return false;
		} else if (!imeMajke.equals(other.imeMajke))
			return false;
		if (imeOca == null) {
			if (other.imeOca != null)
				return false;
		} else if (!imeOca.equals(other.imeOca))
			return false;
		if (isArchive != other.isArchive)
			return false;
		if (isPricest != other.isPricest)
			return false;
		if (krizmanikZahtjevaPosebnuPaznju == null) {
			if (other.krizmanikZahtjevaPosebnuPaznju != null)
				return false;
		} else if (!krizmanikZahtjevaPosebnuPaznju
				.equals(other.krizmanikZahtjevaPosebnuPaznju))
			return false;
		if (mjestoRodjenja == null) {
			if (other.mjestoRodjenja != null)
				return false;
		} else if (!mjestoRodjenja.equals(other.mjestoRodjenja))
			return false;
		if (pohadjaSkolskiVjeronauk == null) {
			if (other.pohadjaSkolskiVjeronauk != null)
				return false;
		} else if (!pohadjaSkolskiVjeronauk
				.equals(other.pohadjaSkolskiVjeronauk))
			return false;
		if (prezimeDjeteta == null) {
			if (other.prezimeDjeteta != null)
				return false;
		} else if (!prezimeDjeteta.equals(other.prezimeDjeteta))
			return false;
		if (prezimeOca == null) {
			if (other.prezimeOca != null)
				return false;
		} else if (!prezimeOca.equals(other.prezimeOca))
			return false;
		if (razred == null) {
			if (other.razred != null)
				return false;
		} else if (!razred.equals(other.razred))
			return false;
		if (skola == null) {
			if (other.skola != null)
				return false;
		} else if (!skola.equals(other.skola))
			return false;
		if (svecenikDolaziNaBlagoslov == null) {
			if (other.svecenikDolaziNaBlagoslov != null)
				return false;
		} else if (!svecenikDolaziNaBlagoslov
				.equals(other.svecenikDolaziNaBlagoslov))
			return false;
		if (zupaKrstenja == null) {
			if (other.zupaKrstenja != null)
				return false;
		} else if (!zupaKrstenja.equals(other.zupaKrstenja))
			return false;
		return true;
	}
}
