package knezija.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.boot.orm.jpa.EntityScan;

@Entity
public class CollectionType {
	@GeneratedValue
	@Id
	private long id;

	@Column
	private String typeName;

	/**
	 * Lista svih kolekcija tog tipa, npr., lista svih kolekcija kojima je tip
	 * slika i video.
	 */
	@OneToMany(mappedBy = "collectionType")
	private List<Kolekcija> collectionsOfType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Kolekcija> getCollectionsOfType() {
		return collectionsOfType;
	}

	public void setCollectionsOfType(List<Kolekcija> collectionsOfType) {
		this.collectionsOfType = collectionsOfType;
	}
	
	
}
