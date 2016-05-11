package knezija.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import knezija.models.Content;
import knezija.models.Kolekcija;
import knezija.models.User;
import knezija.models.forms.CollectionForm;
import knezija.models.forms.ContentForm;
import knezija.models.forms.PostForm;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


/**
 * Offers Content, Kolekcija and similar classes' services to the MVC layer from
 * the database layer. (adapter pattern)
 * 
 * @author Mate-1
 *
 */
@Service
public interface IContentManager {

	Kolekcija createFromForm(CollectionForm collectionForm, long superId, HttpServletRequest req);

	Kolekcija findCollectionById(long id);

	CollectionForm createFromCollection(Kolekcija collection);

	void updateFromForm(CollectionForm collectionForm, long collectionId, HttpServletRequest req);

	void deleteCollection(long id);

	List<String> getAllContentTypes();

	boolean validateUpdateForm(CollectionForm collectionForm,
			BindingResult result);

	//translation
	List<String> getPublicPrivateNames();

	Object getUrlOrFileNames();

	boolean checkValidOnCreate(ContentForm form, BindingResult result, long superId);

	Content createFromForm(ContentForm form, long superId);
	
	Content createPost(PostForm postForm, long superId);
	
	Content updatePost(PostForm form, long id);

	Content findContentById(long id);

	PostForm createFromContent(Content content);

	boolean checkValidOnUpdate(ContentForm form, BindingResult result);

	Content updateFromForm(ContentForm form, long id);

	void deleteContent(long id);

	boolean formIsFile(ContentForm form);

	List<Content> getAllHomepagePosts();
}
