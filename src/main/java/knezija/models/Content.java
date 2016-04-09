package knezija.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.boot.orm.jpa.EntityScan;

@Entity
public class Content {
	@GeneratedValue
	@Id
	private long id;

	@Column
	private String title;

	@Column
	private String description;

	@Column(nullable = false)
	private boolean publicContent;
	
	@Column(nullable = false)
	private boolean isUrlContent;

	// @OneToMany(mappedBy="content")
	// private List<Keyword> keywordsList;

	@ManyToOne
	@JoinColumn
	private User author;

	@ManyToOne
	@JoinColumn
	private Kolekcija collection;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date lastUpdated;

	@ManyToOne
	@JoinColumn
	private ContentType type;

	@Column
	private String url;

	@Lob
	@Column
	private byte[] binaryContent;
	
	@Column
	private String mimeType;
	
	@Column
	private String extraParam;

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

	public Kolekcija getCollection() {
		return collection;
	}

	public void setCollection(Kolekcija collection) {
		this.collection = collection;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte[] getBinaryContent() {
		return binaryContent;
	}

	public void setBinaryContent(byte[] binaryContent) {
		this.binaryContent = binaryContent;
	}

	public boolean isPublicContent() {
		return publicContent;
	}

	public void setPublicContent(boolean publicContent) {
		this.publicContent = publicContent;
	}

	public String getExtraParam() {
		return extraParam;
	}

	public void setExtraParam(String extraParam) {
		this.extraParam = extraParam;
	}

	public boolean isUrlContent() {
		return isUrlContent;
	}

	public void setUrlContent(boolean isUrlContent) {
		this.isUrlContent = isUrlContent;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
