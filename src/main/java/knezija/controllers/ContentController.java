package knezija.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import knezija.models.Content;
import knezija.models.forms.ContentForm;
import knezija.models.forms.PostForm;
import knezija.services.IContentManager;
import knezija.services.IUserManager;
import knezija.utilities.ContentTypes;
import knezija.utilities.NetworkUtilities;
import knezija.utilities.TypedBufferedImage;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ContentController {
	@Autowired
	private IContentManager contentManager;
	@Autowired
	private IUserManager userManager;
	
	@RequestMapping("/collections/{superCollectionId}/create-post-view")
	public String displayCreatePostView(Map<String, Object> model,
			@PathVariable("superCollectionId") String superCollectionId,
			HttpServletRequest req) {
		long longSuperId = Long.parseLong(superCollectionId);
		
		ContentForm form = new PostForm();
		form.setAuthor(userManager.getUser(req).getUsername());
		model.put("postForm", form);
		
		populateCreateContentView(model, longSuperId);
		return "createPost";
	}
	
	@RequestMapping(value = "/collections/{superCollectionId}/create-post-view/create", method = RequestMethod.POST)
	public String createPost(Map<String, Object> model,
			@RequestParam("editorHtml") String editorHTML,
			@Valid @ModelAttribute("postForm") PostForm postForm,
			BindingResult result,
			@PathVariable("superCollectionId") String superCollectionId,
			HttpServletRequest req) {

		
		long superId = Long.parseLong(superCollectionId);
//		PostForm postForm = new PostForm();
//		postForm.setEditorHtml(editorHTML);
		//treba ovaj null zamijenit
		if(contentManager.checkValidOnCreate(postForm, result, superId)) {
			Content content = contentManager.createPost(postForm, superId);
			return "redirect:/collections/" + superId;
		} else {
			populateCreateContentView(model, superId);
			return "createPost";
		}
	}
	
	@RequestMapping("/collections/{superCollectionId}/update-post-view/{id}")
	public String displayUpdatePostView(Map<String, Object> model,
			@PathVariable("superCollectionId") String superCollectionId,
			@PathVariable("id") String postId, HttpServletRequest req) {
		long longSuperId = Long.parseLong(superCollectionId);
		long id = Long.parseLong(postId);

		Content content = contentManager.findContentById(id);
		PostForm form = (PostForm) contentManager.createFromContent(content);
		model.put("postForm", form);

		model.put("id", id);
		populateCreateContentView(model, longSuperId);
		return "createPost";
	}

	@RequestMapping(value = "/collections/{superCollectionId}/update-post-view/{id}/update", method = RequestMethod.POST)
	public String updatePostContent(Map<String, Object> model,
			@Valid @ModelAttribute("postForm") PostForm form,
			BindingResult result,
			@PathVariable("superCollectionId") String superCollectionId,
			@PathVariable("id") String postId, HttpServletRequest req) {

		long superId = Long.parseLong(superCollectionId);
		long id = Long.parseLong(postId);

		if (contentManager.checkValidOnUpdate(form, result)) {
			contentManager.updatePost(form, id);
			model.put("contentUpdatedMessage",
					"Uspješno ste izmijenili objavu.");
		}

		model.put("id", id);
		populateCreateContentView(model, superId);
		return "createPost";
	}

	@RequestMapping("/collections/{superCollectionId}/create-content-view")
	public String displayCreateContentView(Map<String, Object> model,
			@PathVariable("superCollectionId") String superCollectionId,
			HttpServletRequest req) {
		ContentForm form = new ContentForm();
		form.setAuthor(userManager.getUser(req).getUsername());
		model.put("contentForm", form);

		long longSuperId = Long.parseLong(superCollectionId);

		populateCreateContentView(model, longSuperId);
		return "createContent";
	}

	private void populateCreateContentView(Map<String, Object> model,
			long longSuperId) {
		model.put("publicPrivateNames", contentManager.getPublicPrivateNames());
		model.put("urlOrFileNames", contentManager.getUrlOrFileNames());
	}

	@RequestMapping(value = "/collections/{superCollectionId}/create-content-view/create", method = RequestMethod.POST)
	public String createContent(Map<String, Object> model,
			@Valid @ModelAttribute("contentForm") ContentForm form,
			BindingResult result,
			@PathVariable("superCollectionId") String superCollectionId,
			HttpServletRequest req) {

		long superId = Long.parseLong(superCollectionId);
		if (contentManager.checkValidOnCreate(form, result, superId)) {
			Content content = contentManager.createFromForm(form, superId);
			return "redirect:/collections/" + superId;
		} else {
			populateCreateContentView(model, superId);
			return "createContent";
		}
	}

	@RequestMapping("/collections/{superCollectionId}/update-content-view/{id}")
	public String displayUpdateContentView(Map<String, Object> model,
			@PathVariable("superCollectionId") String superCollectionId,
			@PathVariable("id") String collectionId, HttpServletRequest req) {
		long longSuperId = Long.parseLong(superCollectionId);
		long id = Long.parseLong(collectionId);

		Content content = contentManager.findContentById(id);
		ContentForm form = contentManager.createFromContent(content);
		model.put("contentForm", form);

		model.put("id", id);
		populateCreateContentView(model, longSuperId);
		return "createContent";
	}

	@RequestMapping(value = "/collections/{superCollectionId}/update-content-view/{id}/update", method = RequestMethod.POST)
	public String updateContent(Map<String, Object> model,
			@Valid @ModelAttribute("contentForm") ContentForm form,
			BindingResult result,
			@PathVariable("superCollectionId") String superCollectionId,
			@PathVariable("id") String collectionId, HttpServletRequest req) {

		long superId = Long.parseLong(superCollectionId);
		long id = Long.parseLong(collectionId);

		if (contentManager.checkValidOnUpdate(form, result)) {
			contentManager.updateFromForm(form, id);
			model.put("contentUpdatedMessage",
					"Uspješno ste izmijenili podatke o sadržaju.");
		}

		model.put("id", id);
		populateCreateContentView(model, superId);
		return "createContent";
	}

	@RequestMapping(value = "/collections/{superCollectionId}/delete-content/{id}", method = RequestMethod.GET)
	public String deleteContent(Map<String, Object> model,
			@PathVariable("id") String contentId,
			@PathVariable("superCollectionId") String superCollectionId) {

		long id = Long.parseLong(contentId);
		long superId = Long.parseLong(superCollectionId);

		contentManager.deleteContent(id);

		// delete here, redirect to parent collection
		return "redirect:/collections/" + superId;
	}

	@RequestMapping(value = "/collections/{superCollectionId}/content/{id}/binary", method = RequestMethod.GET)
	public void getBinary(HttpServletResponse response,
			@PathVariable("id") String contentId,
			@PathVariable("superCollectionId") String superCollectionId) {
		long id = Long.parseLong(contentId);
		long superId = Long.parseLong(superCollectionId);

		try {
			Content content = contentManager.findContentById(id);
			if (content.isUrlContent()) {

				// String userAgent =
				// "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
				// URL url = new URL(content.getUrl());
				// URLConnection connection = url.openConnection();
				// connection.setRequestProperty("User-Agent", userAgent);
				// return IOUtils.toByteArray(connection.getInputStream());
				response.sendRedirect(content.getUrl());
			} else {
				response.setContentType(content.getMimeType());
				response.getOutputStream().write(content.getBinaryContent());
				response.getOutputStream().flush();
			}
		} catch (IOException ignorable) {
		}
	}

	@RequestMapping(value = "/collections/{superCollectionId}/content/{id}/image", method = RequestMethod.GET)
	public void getImage(HttpServletRequest req, HttpServletResponse response,
			@PathVariable("id") String contentId,
			@PathVariable("superCollectionId") String superCollectionId) {
		long id = Long.parseLong(contentId);
		long superId = Long.parseLong(superCollectionId);

		Content content = contentManager.findContentById(id);
		try {
			response.sendRedirect(NetworkUtilities.getFinalURL(ContentTypes
					.getRepresentationImageUrl(content, id, req)));
		} catch (IOException ignorable) {
		}
	}

	/**
	 * Ovo je jako procesorski zahtjevno. Za svaku sliku iz galerije koja se
	 * prikazuje, server je prvo downloada(potreban brz internet, vjer., nije
	 * toliki problem, osim što može biti dosta slika), onda je scalea(to je
	 * procesorski zahtjevno), onda joj odredi tip(ne znam koliko je zahtjevno)
	 * i onda je pošalje korisniku(šalje manju sliku, pa onda je to ubrzavajući
	 * faktor).
	 * 
	 * @param req
	 * @param response
	 * @param contentId
	 * @param superCollectionId
	 * @param desiredWidth
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/collections/{superCollectionId}/content/{id}/image/{width}", method = RequestMethod.GET)
	public Object getScaledImage(HttpServletRequest req,
			HttpServletResponse response, @PathVariable("id") String contentId,
			@PathVariable("superCollectionId") String superCollectionId,
			@PathVariable("width") String desiredWidth) {
		long id = Long.parseLong(contentId);
		long superId = Long.parseLong(superCollectionId);
		int width = Integer.parseInt(desiredWidth);

		String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";

		BufferedImage image = null;
		Content content = contentManager.findContentById(id);
		String formatName = "jpg";
		try {
			String destinationURL = NetworkUtilities.getFinalURL(ContentTypes
					.getRepresentationImageUrl(content, id, req));
			URL url = new URL(destinationURL);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestProperty("User-Agent", userAgent);
			TypedBufferedImage typedImage = ContentTypes
					.getTypedImage(connection.getInputStream());
			image = typedImage.getBufferedImage();
			formatName = typedImage.getFormatName();
		} catch (IOException ignorable) {
			ignorable.printStackTrace();
		}

		image = Scalr.resize(image, Method.QUALITY, width);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, formatName, outputStream);
		} catch (IOException e) {
			System.err
					.println("An error occurred while writing image to output stream.");
		}
		return outputStream.toByteArray();
	}

	long iconCollectionId = 11;
	long docIconId = 28;
	long wordIconId = 29;
	long pdfIconId = 30;
	long folderIconId = 31;
	long postIconId = 32;

	@RequestMapping(value = "/images/doc-image", method = RequestMethod.GET)
	public String getDocIcon(HttpServletResponse response) {
		return "redirect:/collections/" + iconCollectionId + "/content/"
				+ docIconId + "/binary";
	}

	@RequestMapping(value = "/images/word-image", method = RequestMethod.GET)
	public String getWordIcon() {
		return "redirect:/collections/" + iconCollectionId + "/content/"
				+ wordIconId + "/binary";
	}

	@RequestMapping(value = "/images/pdf-image", method = RequestMethod.GET)
	public String getPdfIcon() {
		return "redirect:/collections/" + iconCollectionId + "/content/"
				+ pdfIconId + "/binary";
	}

	@RequestMapping(value = "/images/folder-image", method = RequestMethod.GET)
	public String getMapIcon() {
		return "redirect:/collections/" + iconCollectionId + "/content/"
				+ folderIconId + "/binary";
	}

	@RequestMapping(value = "/images/post-image", method = RequestMethod.GET)
	public String getPostIcon() {
		return "redirect:/collections/" + iconCollectionId + "/content/"
				+ postIconId + "/binary";
	}
}
