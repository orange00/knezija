package knezija.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.boot.orm.jpa.EntityScan;

@Entity
public class CollectionContentTypes {
	@GeneratedValue
	@Id
	private long id;
	
	@ManyToOne
	@JoinColumn
	private ContentType contentType;
	
	@ManyToOne
	@JoinColumn
	private Kolekcija collection;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public Kolekcija getCollection() {
		return collection;
	}

	public void setCollection(Kolekcija collection) {
		this.collection = collection;
	}
	
	
}
