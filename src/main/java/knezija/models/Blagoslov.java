package knezija.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.jpa.criteria.predicate.IsEmptyPredicate;

//blagoslov bi možda trebao imati datum prijave
@Entity
public class Blagoslov implements Prijava, Record<Blagoslov> {
	@GeneratedValue
	@Id
	private long id;

	@Column
	private String ime;
	@Column
	private String prezime;
	@ManyToOne
	@JoinColumn
	private Ulica ulicaIDatum;
	@Column
	private int kucniBroj;
	@Column
	private int kat;
	@Column
	private int brojStana;
	@Column
	private String vrijemeBlagoslova;
	@Column
	private String vrijemeBlagoslovaOstalo;
	@Column
	private String prijePoslijeBozica;
	@Column
	private String email;
	@Column
	private String brojTelefona;
	@Lob
	private String napomene;

	private boolean isArchive;

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

	public Ulica getUlicaIDatum() {
		return ulicaIDatum;
	}

	public void setUlicaIDatum(Ulica ulicaIDatum) {
		this.ulicaIDatum = ulicaIDatum;
	}

	public int getKucniBroj() {
		return kucniBroj;
	}

	public void setKucniBroj(int kucniBroj) {
		this.kucniBroj = kucniBroj;
	}

	public int getKat() {
		return kat;
	}

	public void setKat(int kat) {
		this.kat = kat;
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

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public int getBrojStana() {
		return brojStana;
	}

	public void setBrojStana(int brojStana) {
		this.brojStana = brojStana;
	}

	public String getVrijemeBlagoslovaOstalo() {
		return vrijemeBlagoslovaOstalo;
	}

	public void setVrijemeBlagoslovaOstalo(String vrijemeBlagoslovaOstalo) {
		this.vrijemeBlagoslovaOstalo = vrijemeBlagoslovaOstalo;
	}

	@Override
	public boolean logicallyEquals(Blagoslov obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Blagoslov other = (Blagoslov) obj;
		if (brojStana != other.brojStana)
			return false;
		if (brojTelefona == null) {
			if (other.brojTelefona != null)
				return false;
		} else if (!brojTelefona.equals(other.brojTelefona))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (isArchive != other.isArchive)
			return false;
		if (kat != other.kat)
			return false;
		if (kucniBroj != other.kucniBroj)
			return false;
		if (napomene == null) {
			if (other.napomene != null)
				return false;
		} else if (!napomene.equals(other.napomene))
			return false;
		if (prezime == null) {
			if (other.prezime != null)
				return false;
		} else if (!prezime.equals(other.prezime))
			return false;
		if (prijePoslijeBozica == null) {
			if (other.prijePoslijeBozica != null)
				return false;
		} else if (!prijePoslijeBozica.equals(other.prijePoslijeBozica))
			return false;
		if (ulicaIDatum == null) {
			if (other.ulicaIDatum != null)
				return false;
		} else if (!ulicaIDatum.equals(other.ulicaIDatum))
			return false;
		if (vrijemeBlagoslova == null) {
			if (other.vrijemeBlagoslova != null)
				return false;
		} else if (!vrijemeBlagoslova.equals(other.vrijemeBlagoslova))
			return false;
		if (!isEmptyOrNull(vrijemeBlagoslovaOstalo)
				|| !isEmptyOrNull(other.vrijemeBlagoslovaOstalo)) {
			if (vrijemeBlagoslovaOstalo == null) {
				if (other.vrijemeBlagoslovaOstalo != null)
					return false;
			} else if (!vrijemeBlagoslovaOstalo
					.equals(other.vrijemeBlagoslovaOstalo))
				return false;
		}
		return true;
	}

	private boolean isEmptyOrNull(String string) {
		return string==null || string.trim().isEmpty();
	}

}
