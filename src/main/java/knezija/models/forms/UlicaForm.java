package knezija.models.forms;

import org.hibernate.validator.constraints.NotEmpty;


public class UlicaForm {
	private long id;
	
	@NotEmpty(message="Naziv ulice je obvezan!")
	private String nazivUlice;
	private String datum;
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
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
}
