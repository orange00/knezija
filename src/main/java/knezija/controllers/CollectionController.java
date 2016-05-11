package knezija.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import knezija.models.Content;
import knezija.models.Kolekcija;
import knezija.models.forms.CollectionForm;
import knezija.services.IContentManager;
import knezija.services.IUserManager;
import knezija.utilities.ContentTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CollectionController {
	@Autowired
	private IContentManager contentManager;
	@Autowired
	private IUserManager userManager;

	public CollectionController() {
	}

	// dodati da se može sortirati po nekim parametrima
	// parametri se prenose kao atributi
	/**
	 * Returns a view which displays all the collections in a gallery like(or
	 * system explorer like) view.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/collections/{id}")
	public String displayCollectionTree(Map<String, Object> model,
			@PathVariable("id") String id) {
		long longId = Long.parseLong(id);

		populateCollectionView(model, longId);
		return "collectionView";
	}

	/**
	 * Displays all the collection content like a feed, one content beneath the
	 * other. The first displayed content will be the newest created content,
	 * and the last will be the oldest created content if the newestToOldest is
	 * true for the collection. Otherwise, the order will be opposite(oldest to
	 * newest). The order of content will not change when the content is
	 * updated.
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/collections/{id}/feed")
	public String displayCollectionFeed(Map<String, Object> model,
			@PathVariable("id") String id) {
		long longId = Long.parseLong(id);

		populateCollectionFeed(model, longId);
		return "collectionFeedView";
	}

	/**
	 * Populates the view of the given collection(by id) with the children of
	 * that collection. The children of that collection are other
	 * collections(maps) and content(videos, images, ...)
	 * 
	 * @param model
	 * @param collectionId
	 */
	private void populateCollectionView(Map<String, Object> model,
			long collectionId) {
		Kolekcija collection = contentManager.findCollectionById(collectionId);
		List<Content> collectionContent = new ArrayList<>(
				collection.getCollectionContent());
		Collections.sort(collectionContent,
				(content1, content2) -> -content1.getType().getTypeName()
						.compareTo(content2.getType().getTypeName()));
		model.put("contentList", collectionContent);
		model.put("subCollectionsList", collection.getSubCollections());
		model.put("collection", collection);
	}

	private void populateCollectionFeed(Map<String, Object> model, long longId) {
		populateCollectionView(model, longId);
		List<Content> content = (List<Content>) model.get("contentList");
		Kolekcija collection = (Kolekcija) model.get("collection");
		Comparator<Content> comparator = (c1, c2) -> Long.valueOf(c1.getId())
				.compareTo(c2.getId());
		comparator = collection.isNewestToOldest() ? comparator.reversed()
				: comparator;

		model.put(
				"allContent",
				content.stream()
						.map((cont) -> contentManager.createFromContent(cont))
						.collect(Collectors.toList()));

		Collections.sort(content, comparator);
	}

	@RequestMapping("/collections/{superCollectionId}/create")
	public String displayCreateCollectionView(Map<String, Object> model,
			@PathVariable("superCollectionId") String superCollectionId,
			HttpServletRequest req) {
		long longSuperId = Long.parseLong(superCollectionId);
		CollectionForm form = new CollectionForm();
		form.setAuthor(userManager.getUser(req).getUsername());
		Kolekcija superKolekcija = contentManager
				.findCollectionById(longSuperId);
		form.setCollectionContentTypes(ContentTypes.getNames(
				superKolekcija.getCollectionContentTypesList()).toArray(
				new String[0]));

		model.put("collectionForm", form);

		populateCreateCollectionView(model, longSuperId);
		return "createCollection";
	}

	private void populateCreateCollectionView(Map<String, Object> model,
			long longSuperId) {
		model.put("superCollectionId", longSuperId);
		model.put("allContentTypes", contentManager.getAllContentTypes());
		model.put("publicPrivateNames", contentManager.getPublicPrivateNames());
	}

	@RequestMapping(value = "/collections/{superCollectionId}/create/save", method = RequestMethod.POST)
	public String createCollection(
			Map<String, Object> model,
			@Valid @ModelAttribute("collectionForm") CollectionForm collectionForm,
			BindingResult result,
			@PathVariable("superCollectionId") String superCollectionId,
			HttpServletRequest req) {

		long superId = Long.parseLong(superCollectionId);
		Kolekcija superCollection = contentManager.findCollectionById(superId);

		if (!result.hasErrors()
				|| !ContentTypes.compareContentTypes(collectionForm,
						superCollection, result)) {
			Kolekcija collection = contentManager.createFromForm(
					collectionForm, superId, req);
			return "redirect:/collections/" + collection.getId();
		} else {
			populateCreateCollectionView(model, superId);
			return "createCollection";
		}
	}

	// razlika izmedju create i update kod viewa je samo link na koji se šalje
	// to se može nekako riješit da se dinamički submit gumbu promijeni link,
	// npr., ako je spremljen id u model, onda ide na update, inače na create

	@RequestMapping("/collections/{superCollectionId}/update/{id}")
	public String updateCollectionView(Map<String, Object> model,
			@PathVariable("id") String collectionId,
			@PathVariable("superCollectionId") String superCollectionId) {

		long id = Long.parseLong(collectionId);
		long superId = Long.parseLong(superCollectionId);

		Kolekcija collection = contentManager.findCollectionById(id);
		CollectionForm form = contentManager.createFromCollection(collection);
		model.put("collectionForm", form);

		model.put("id", id);
		populateCreateCollectionView(model, superId);
		return "createCollection";
	}

	// ovdje ubaciti message neki: uspješno izmijenjeno,...
	@RequestMapping(value = "/collections/{superCollectionId}/update/{id}/save", method = RequestMethod.POST)
	public String updateCollection(
			Map<String, Object> model,
			@Valid @ModelAttribute("collectionForm") CollectionForm collectionForm,
			BindingResult result, @PathVariable("id") String collectionId,
			@PathVariable("superCollectionId") String superCollectionId,
			HttpServletRequest req) {

		long id = Long.parseLong(collectionId);
		long superId = Long.parseLong(superCollectionId);
		Kolekcija superCollection = contentManager.findCollectionById(superId);

		if (!result.hasErrors()
				|| !ContentTypes.compareContentTypes(collectionForm,
						superCollection, result)) {
			contentManager.updateFromForm(collectionForm, id, req);
			model.put("collectionUpdatedMessage",
					"Uspješno ste izmijenili podatke o mapi.");
		}

		model.put("id", id);
		populateCreateCollectionView(model, superId);
		return "createCollection";
	}

	// izbaciti neki warning da će brisanje kolekcije izbrisati sav sadržaj na
	// kolekciju
	@RequestMapping(value = "/collections/{superCollectionId}/delete/{id}", method = RequestMethod.GET)
	public String deleteCollection(Map<String, Object> model,
			@PathVariable("id") String collectionId,
			@PathVariable("superCollectionId") String superCollectionId) {

		long id = Long.parseLong(collectionId);
		long superId = Long.parseLong(superCollectionId);

		contentManager.deleteCollection(id);

		// delete here, redirect to parent collection
		return "redirect:/collections/" + superId;
	}
}
