package knezija.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Kolekcija {
	@GeneratedValue
	@Id
	private long id;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@ManyToOne
	@JoinColumn
	private User author;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date lastUpdated;
	
	@ManyToOne
	@JoinColumn
	private Kolekcija superCollection;
	
	@OneToMany(mappedBy="superCollection", cascade = CascadeType.REMOVE)
	private List<Kolekcija> subCollections;
	
	@ManyToOne
	@JoinColumn
	private CollectionType collectionType;
	
	@Column(nullable = false)
	private boolean publicCollection;
	
	/**
	 * Lista svih odabranih tipova sadržaja za ovu kolekciju.
	 */
	@OneToMany(mappedBy="collection", cascade = CascadeType.REMOVE)
	private List<CollectionContentTypes> collectionContentTypesList;
	
	@OneToMany(mappedBy = "collection", cascade = CascadeType.REMOVE)
	private List<Content> collectionContent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Kolekcija getSuperCollection() {
		return superCollection;
	}

	public void setSuperCollection(Kolekcija superCollection) {
		this.superCollection = superCollection;
	}

	public CollectionType getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(CollectionType collectionType) {
		this.collectionType = collectionType;
	}

	public List<Kolekcija> getSubCollections() {
		return subCollections;
	}

	public void setSubCollections(List<Kolekcija> subCollections) {
		this.subCollections = subCollections;
	}

	public List<CollectionContentTypes> getCollectionContentTypesList() {
		return collectionContentTypesList;
	}

	public void setCollectionContentTypesList(
			List<CollectionContentTypes> collectionContentTypesList) {
		this.collectionContentTypesList = collectionContentTypesList;
	}

	public List<Content> getCollectionContent() {
		return collectionContent;
	}

	public void setCollectionContent(List<Content> collectionContent) {
		this.collectionContent = collectionContent;
	}

	public boolean isPublicCollection() {
		return publicCollection;
	}

	public void setPublicCollection(boolean publicCollection) {
		this.publicCollection = publicCollection;
	}
}
