package knezija.utilities;

import java.awt.image.BufferedImage;

public class TypedBufferedImage {
	private BufferedImage bufferedImage; 
	private String formatName;
	

	public TypedBufferedImage(BufferedImage bi, String formatName) {
		bufferedImage = bi;
		this.formatName = formatName;
	}


	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}


	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}


	public String getFormatName() {
		return formatName;
	}


	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	
}
