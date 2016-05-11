package knezija.models.forms;

public abstract class SynchronizableForm {
	private String inputColor;
	
	public String getInputColor() {
		return inputColor;
	}

	public void setInputColor(String inputColor) {
		this.inputColor = inputColor;
	}



	public abstract long getId();
}
