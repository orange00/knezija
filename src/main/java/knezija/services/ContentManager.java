package knezija.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import knezija.models.CollectionContentTypes;
import knezija.models.CollectionType;
import knezija.models.Content;
import knezija.models.ContentType;
import knezija.models.Kolekcija;
import knezija.models.User;
import knezija.models.forms.CollectionForm;
import knezija.models.forms.ContentForm;
import knezija.persistence.IDao;
import knezija.utilities.ContentTypes;
import knezija.utilities.VideoParser;

@Service
public class ContentManager implements IContentManager {
	@Autowired
	private IDao dao;

	@Autowired
	private IUserManager userManager;

	private static List<String> allContentTypes;
	private static List<String> publicPrivateNames;
	private static List<String> urlOrFileNames;

	static {
		publicPrivateNames = new ArrayList<String>();
		publicPrivateNames.add("Javna");
		publicPrivateNames.add("Privatna");

		urlOrFileNames = new ArrayList<String>();
		urlOrFileNames.add("Url");
		urlOrFileNames.add("Datoteka");
	}

	@Override
	public Kolekcija createFromForm(CollectionForm collectionForm,
			long superId, HttpServletRequest req) {
		Kolekcija collection = new Kolekcija();
		collectionChanged(collection, collectionForm, req);
		Kolekcija superCollection = dao.findById(Kolekcija.class, superId);
		collection.setSuperCollection(superCollection);
		// unutarnja
		collection.setCollectionType(dao.findById(CollectionType.class, 4L));
		collection = dao.update(collection);

		List<String> selectedCollectionTypesString = Arrays
				.asList(collectionForm.getCollectionContentTypes());
		List<CollectionContentTypes> collectionContentTypes = new ArrayList<>();
		for (String contentTypeName : selectedCollectionTypesString) {
			CollectionContentTypes collectionContentType = new CollectionContentTypes();
			ContentType contentType = dao.find(ContentType.class, "typeName",
					contentTypeName);
			collectionContentType.setCollection(collection);
			collectionContentType.setContentType(contentType);
			collectionContentType = dao.update(collectionContentType);
			collectionContentTypes.add(collectionContentType);
		}
		collection.setCollectionContentTypesList(collectionContentTypes);

		return collection;
	}

	/**
	 * Updates the given Kolekcija instance, from the given form. The
	 * contentCollection types will not be updated since they cannot be changed
	 * for a collection.
	 * 
	 * @param collection
	 * @param collectionForm
	 * @param author
	 */
	private void collectionChanged(Kolekcija collection,
			CollectionForm collectionForm, HttpServletRequest req) {
		User author = (User) req.getSession().getAttribute("user");
		collection.setTitle(collectionForm.getTitle());
		collection.setDescription(collectionForm.getDescription());
		collection.setLastUpdated(new Date());
		collection.setPublicCollection(collectionForm.getPublicCollection()
				.equals("Javna"));

		collection.setAuthor(author);
	}

	@Override
	public Kolekcija findCollectionById(long id) {
		return dao.findById(Kolekcija.class, id);
	}

	@Override
	public CollectionForm createFromCollection(Kolekcija collection) {
		CollectionForm collectionForm = new CollectionForm();
		collectionForm.setTitle(collection.getTitle());
		collectionForm.setDescription(collection.getDescription());
		collectionForm
				.setPublicCollection(collection.isPublicCollection() ? "Javna"
						: "Privatna");
		collectionForm.setCollectionContentTypes(collection
				.getCollectionContentTypesList()
				.stream()
				.map((collectionContentType) -> collectionContentType
						.getContentType().getTypeName())
				.collect(Collectors.toList()).toArray(new String[0]));

		return collectionForm;
	}

	@Override
	public void updateFromForm(CollectionForm collectionForm,
			long collectionId, HttpServletRequest req) {
		Kolekcija collection = dao.findById(Kolekcija.class, collectionId);
		collectionChanged(collection, collectionForm, req);
		dao.update(collection);
	}

	@Override
	public void deleteCollection(long id) {
		dao.remove(dao.findById(Kolekcija.class, id));
	}

	@Override
	public List<String> getAllContentTypes() {
		if (allContentTypes == null) {
			allContentTypes = dao.findAll(ContentType.class).stream()
					.map((contentType) -> contentType.getTypeName())
					.collect(Collectors.toList());
		}

		return allContentTypes;
	}

	@Override
	public boolean validateUpdateForm(CollectionForm collectionForm,
			BindingResult result) {
		if (!result.hasErrors()) {
			return true;
		}

		return result.getErrorCount() == 1
				&& result.hasFieldErrors("collectionContentTypes");
	}

	@Override
	public List<String> getPublicPrivateNames() {
		return publicPrivateNames;
	}

	@Override
	public Object getUrlOrFileNames() {
		// TODO Auto-generated method stub
		return urlOrFileNames;
	}

	@Override
	public boolean checkValidOnCreate(ContentForm form, BindingResult result, long superCollectionId) {
		if (!checkValidOnUpdate(form, result)) {
			return false;
		}

		if (form.getContentLocation().equals(
				ContentManager.urlOrFileNames.get(1))) {
			if (form.getFile() == null || form.getFile().isEmpty()) {
				result.rejectValue("file", "", "Morate odabrati datoteku!");
				return false;
			}
		}
		
		Kolekcija superCollection = findCollectionById(superCollectionId);
		boolean contentTypesValid = ContentTypes.compareContentTypes(form, superCollection, result);

		return true;
	}

	@Override
	public Content createFromForm(ContentForm form, long superId) {
		Content content = new Content();
		Kolekcija superCollection = findCollectionById(superId);
		content.setCollection(superCollection);
		User author = userManager.findByUsername(form.getAuthor());
		content.setAuthor(author);

		contentChanged(content, form);
		return dao.update(content);
	}

	@Override
	public Content updateFromForm(ContentForm form, long id) {
		Content content = findContentById(id);
		contentChanged(content, form);
		return dao.update(content);
	}

	private void contentChanged(Content content, ContentForm form) {
		boolean contentTypePossiblyChanged = true;
		String contentType = "";
		
		boolean isUrl = false;
		String url = "";
		byte[] binary = new byte[0];
		//file selected
		if(form.getContentLocation().equals(urlOrFileNames.get(1))) {
			MultipartFile file = form.getFile();
			//new file selected
			if(file!=null && !file.isEmpty()) {
				try {
					binary = file.getBytes();
				} catch (IOException ignorable) {
				}
				contentType = file.getContentType();
				
				//no file selected on update, keep the old file
			} else {
				binary = content.getBinaryContent();
				contentTypePossiblyChanged = false;
			}
		//url selected
		} else {
			url = form.getUrl();
			isUrl = true;
			contentType = ContentTypes.probeMimeFromUrl(url);
		}
		content.setBinaryContent(binary);
		if(contentTypePossiblyChanged) {
			String databaseTypeName = ContentTypes.getDatabaseNameFromMime(contentType, url);
			ContentType type = dao.find(ContentType.class, "typeName", databaseTypeName);
			content.setType(type);
			content.setMimeType(contentType);
		}
		
		content.setDescription(form.getDescription());
		content.setLastUpdated(new Date());
		content.setPublicContent(form.getPublicContent().equals("Javna"));
		content.setTitle(form.getTitle());
		content.setUrl(url);
		content.setUrlContent(isUrl);
		
		if(ContentTypes.isVideo(content)) {
			String dataYoutube = VideoParser.getDataYoutube(url);
			content.setExtraParam(dataYoutube);
		}
	}

	@Override
	public Content findContentById(long id) {
		return dao.findById(Content.class, id);
	}

	@Override
	public ContentForm createFromContent(Content content) {
		ContentForm form = new ContentForm();
		form.setAuthor(content.getAuthor().getUsername());
		form.setContentLocation(content.isUrlContent() ? urlOrFileNames.get(0) : urlOrFileNames.get(1));
		form.setDescription(content.getDescription());
		form.setPublicContent(content.isPublicContent() ? "Javna" : "Privatna");
		form.setTitle(content.getTitle());
		form.setUrl(content.getUrl());
		
		return form;
	}

	@Override
	public boolean checkValidOnUpdate(ContentForm form, BindingResult result) {
		if (result.hasErrors()) {
			return false;
		}

		// url is selected
		if (form.getContentLocation().equals(
				ContentManager.urlOrFileNames.get(0))) {
			if (form.getUrl().isEmpty()) {
				result.rejectValue("url", "", "Morate unijeti url sadržaja!");
				return false;
			}
		}

		return true;
	}

	@Override
	public void deleteContent(long id) {
		Content content = dao.findById(Content.class, id);
		dao.remove(content);
	}
}
