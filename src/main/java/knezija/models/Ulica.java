package knezija.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.joda.time.DateTimeComparator;

/**
 * Ulica s pripadnim datumima za blagoslov.
 */
@Entity
public class Ulica {
	@GeneratedValue
	@Id
	private long id;

	@Column(unique = true)
	private String nazivUlice;
	@Column
	private Date datum;
	// mozda dodati datumUDosascu i datumNakonBozica, jer ljudi to biraju

	@OneToMany(mappedBy = "ulicaIDatum")
	List<Blagoslov> blagosloviUUlici;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNazivUlice() {
		return nazivUlice;
	}

	public void setNazivUlice(String nazivUlice) {
		this.nazivUlice = nazivUlice;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public List<Blagoslov> getBlagosloviUUlici() {
		return blagosloviUUlici;
	}

	public void setBlagosloviUUlici(List<Blagoslov> blagosloviUUlici) {
		this.blagosloviUUlici = blagosloviUUlici;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((nazivUlice == null) ? 0 : nazivUlice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ulica other = (Ulica) obj;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (DateTimeComparator.getDateOnlyInstance().compare(datum, other.datum)!=0)
			return false;
		if (id != other.id)
			return false;
		if (nazivUlice == null) {
			if (other.nazivUlice != null)
				return false;
		} else if (!nazivUlice.equals(other.nazivUlice))
			return false;
		return true;
	}
	
	
}
