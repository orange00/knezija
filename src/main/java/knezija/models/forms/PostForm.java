package knezija.models.forms;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import knezija.models.Content;

public class PostForm extends ContentForm {
	private String editorHtml;

	/**
	 * Should be a date representation in Croatian format.
	 */
	private String lastUpdated;

	private String id;

	public String getEditorHtml() {
		return editorHtml;
	}

	public void setEditorHtml(String editorHtml) {
		this.editorHtml = editorHtml;
	}

	@Override
	public void updateTypeSpecificsFromContent(Content content) {
		super.updateTypeSpecificsFromContent(content);

		editorHtml = new String(content.getBinaryContent(),
				StandardCharsets.UTF_8);
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		lastUpdated = formatter.format(content.getLastUpdated());
		id = String.valueOf(content.getId());

		setExtraParam(content.getExtraParam());
		setTypeName(content.getType().getTypeName());
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
