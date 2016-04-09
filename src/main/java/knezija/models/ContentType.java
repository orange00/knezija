package knezija.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ContentType {
	@GeneratedValue
	@Id
	private long id;
	
	@Column(unique = true)
	private String typeName;

	/**
	 * Ex., all images or all video,...
	 */
	@OneToMany(mappedBy = "type")
	private List<Content> allContentOfType;

	/**
	 * This will probably never be used.
	 */
	@OneToMany(mappedBy = "contentType")
	private List<CollectionContentTypes> allCollectionContentTypesOfType;

	
	
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

	public List<Content> getAllContentOfType() {
		return allContentOfType;
	}

	public void setAllContentOfType(List<Content> allContentOfType) {
		this.allContentOfType = allContentOfType;
	}

	public List<CollectionContentTypes> getAllCollectionContentTypesOfType() {
		return allCollectionContentTypesOfType;
	}

	public void setAllCollectionContentTypesOfType(
			List<CollectionContentTypes> allCollectionContentTypesOfType) {
		this.allCollectionContentTypesOfType = allCollectionContentTypesOfType;
	}

	
}
