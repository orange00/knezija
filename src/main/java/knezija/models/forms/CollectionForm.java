package knezija.models.forms;

import org.hibernate.validator.constraints.NotEmpty;

import knezija.models.CollectionType;

public class CollectionForm {
	@NotEmpty(message = "Naslov mape ne može biti prazan!")
	private String title;
	private String description;
	private String author;
	@NotEmpty(message = "Morate odabrati barem jedan tip sadržaja!")
	private String[] collectionContentTypes;
	@NotEmpty(message = "Morate odabrati javnost mape")
	private String publicCollection;

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

	public String[] getCollectionContentTypes() {
		return collectionContentTypes;
	}

	public void setCollectionContentTypes(String[] collectionContentTypes) {
		this.collectionContentTypes = collectionContentTypes;
	}

	public String getPublicCollection() {
		return publicCollection;
	}

	public void setPublicCollection(String publicCollection) {
		this.publicCollection = publicCollection;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
