package knezija.models.forms;

import knezija.models.Content;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class ContentForm {
	@NotEmpty(message = "Naziv sadržaja ne može biti prazan!")
	private String title;
	private String description;
	private String author;
	@NotEmpty(message = "Morate odabrati javnost mape")
	private String publicContent;
	/**
	 * Whether the content is referenced by url or file.
	 * "Url" vs "Datoteka"
	 */
	@NotEmpty(message = "Morate odabrati lokaciju sadržaja")
	private String contentLocation;
	private String url;
	private MultipartFile file;
	
	private String extraParam;
	
	private String typeName;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getPublicContent() {
		return publicContent;
	}

	public void setPublicContent(String publicContent) {
		this.publicContent = publicContent;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContentLocation() {
		return contentLocation;
	}

	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}

	public void updateTypeSpecificsFromContent(Content content) {
	}

	public String getExtraParam() {
		return extraParam;
	}

	public void setExtraParam(String extraParam) {
		this.extraParam = extraParam;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
