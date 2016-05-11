package knezija.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import knezija.models.Content;
import knezija.models.forms.PostForm;
import knezija.services.IContentManager;
import knezija.utilities.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.emory.mathcs.backport.java.util.Collections;

@Controller
public class HomepageAndPostsController {
	@Autowired
	private IContentManager contentManager;

	@RequestMapping("/homepage")
	public String displayHomepageView(Map<String, Object> model) {
		populateDisplayHomepageView(model);
		return "homepage";
	}

	private void populateDisplayHomepageView(Map<String, Object> model) {
		List<Content> contentPosts = contentManager.getAllHomepagePosts();
		Comparator<Content> comparator = (content1, content2)->content1.getLastUpdated().compareTo(content2.getLastUpdated());
		Collections.sort(contentPosts, comparator.reversed());
		List<PostForm> posts = contentPosts.stream()
				.map((content)->contentManager.createFromContent(content)).collect(Collectors.toList());
		model.put("posts", posts);
		model.put("pictureUrl", Constants.HOMEPAGE_BACKGROUND_PICTURE_URL);
		model.put("parishInfoPost", contentManager.createFromContent(contentManager.findContentById(Constants.HOMEPAGE_PARISH_INFO_POST_ID)));
		model.put("contactInfoPost", contentManager.createFromContent(contentManager.findContentById(Constants.HOMEPAGE_PARISH_CONTACT_POST_ID)));
	}
}
