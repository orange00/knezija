package knezija.utilities;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import knezija.models.CollectionContentTypes;
import knezija.models.Content;
import knezija.models.Kolekcija;
import knezija.models.forms.CollectionForm;
import knezija.models.forms.ContentForm;
import knezija.models.forms.PostForm;
import knezija.services.ContentManager;
import knezija.services.IContentManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

public class ContentTypes {
	private static IContentManager contentManager = new ContentManager();

	public static String probeMimeFromUrl(String urlname) {
		String testUrl = urlname.toLowerCase();
		if (VideoParser.isVideoUrl(urlname)) {
			return "Video";
		}

		try {
			URL url = new URL(urlname);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("HEAD");
			connection.connect();
			String contentType = connection.getContentType();
			return contentType;
		} catch (Exception exc) {
			return "";
		}
	}

	/**
	 * The url can be provided if it exists. For videos url should be the full
	 * embed code. If the url does not exist, provide an empty string(not null).
	 * 
	 * @param contentType
	 * @param urlName
	 * @return
	 */
	public static String getDatabaseNameFromMime(String contentType,
			String urlName) {
		contentType = contentType.toLowerCase();
		urlName = urlName.toLowerCase();
		if (contentType.contains("video")) {
			return "Video";
		} else if (contentType.contains("image")) {
			return "Slika";
		} else if (contentType.contains("audio")) {
			return "Audio";
		}else if(contentType.contains("html")) {
			return "Objava";
		} else {
			if (VideoParser.isVideoUrl(urlName)) {
				return "Video";
			}

			return "Dokument";
		}
	}

	/**
	 * Compares the content types of the given collection(given as form) and
	 * it's super collection to see if the content types match.
	 * 
	 * @param collectionForm
	 * @param superCollection
	 * @param result
	 * @return
	 */
	public static boolean compareContentTypes(CollectionForm collectionForm,
			Kolekcija superCollection, BindingResult result) {
		List<String> contentTypes = Arrays.asList(collectionForm
				.getCollectionContentTypes());
		List<String> superContentTypes = getNames(superCollection
				.getCollectionContentTypesList());

		boolean isValid = superContentTypes.containsAll(contentTypes);
		if(!isValid) {
			result.rejectValue("collectionContentTypes", "", "Niste odabrali ispravan tip sadržaja!");
		}
		
		return isValid;
	}
	
	public static boolean compareContentTypes(ContentForm form,
			Kolekcija superCollection, BindingResult result) {
		String contentType = getContentTypeDatabaseNameFromForm(form);
		List<String> superContentTypes = getNames(superCollection
				.getCollectionContentTypesList());

		boolean isValid = superContentTypes.contains(contentType);
		if(!isValid) {
			result.rejectValue("file", "", "Mapa ne podržava tip odabranog sadržaja!");
		}
		
		return isValid;
	}

	public static String getContentTypeDatabaseNameFromForm(ContentForm form) {
		if(form instanceof PostForm && ((PostForm)form).getEditorHtml()!=null) {
			return "Objava";
		}
		if(contentManager.formIsFile(form)) {
			return getDatabaseNameFromMime(form.getFile().getContentType(), "");
		} else {
			return getDatabaseNameFromMime(probeMimeFromUrl(form.getUrl()), form.getUrl());
		}
	}

	public static List<String> getNames(
			List<CollectionContentTypes> collectionContentTypesList) {
		return collectionContentTypesList.stream()
				.map((type) -> type.getContentType().getTypeName())
				.collect(Collectors.toList());
	}

	public static String getRepresentationImageUrl(Content content,
			long superId, HttpServletRequest request) {
		String baseUrl = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();

		String contentTypeName = content.getType().getTypeName();
		switch (contentTypeName) {
		case "Slika":
			return baseUrl + "/collections/" + superId + "/content/"
					+ content.getId() + "/binary";
		case "Video":
			String dataYoutube = VideoParser.getDataYoutube(content.getUrl());
			return "http://img.youtube.com/vi/" + dataYoutube
					+ "/hqdefault.jpg";
		case "Dokument":
			String mimeType = content.getMimeType().toLowerCase();
			if (mimeType.contains("word")) {
				return baseUrl + "/images/word-image";
			} else if (mimeType.contains("pdf")) {
				return baseUrl + "/images/pdf-image";
			} else {
				return baseUrl + "/images/doc-image";
			}
		case "Objava":
			return baseUrl + "/images/post-image";
		case "Audio":
			return baseUrl + "/images/audio-image";
		default:
			return "";
		}
	}

	public static boolean isVideo(Content content) {
		return content.getType().getTypeName().equalsIgnoreCase("video");
	}

	/**
	 * Doesn't close the stream.
	 * 
	 * @param input
	 * @return
	 */
	public static TypedBufferedImage getTypedImage(InputStream input) {
		BufferedImage bi = null;
		try {
			ImageInputStream stream = ImageIO.createImageInputStream(input);

			Iterator iter = ImageIO.getImageReaders(stream);
			if (!iter.hasNext()) {
				return null;
			}
			ImageReader reader = (ImageReader) iter.next();
			ImageReadParam param = reader.getDefaultReadParam();
			reader.setInput(stream, true, true);
			try {
				bi = reader.read(0, param);
				return new TypedBufferedImage(bi, reader.getFormatName());
			} finally {
				// reader.dispose();
				// stream.close();
			}
		} catch (Exception exc) {
			return new TypedBufferedImage(bi, "jpg");
		}
	}
}
