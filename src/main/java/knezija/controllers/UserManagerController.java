package knezija.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import knezija.models.User;
import knezija.models.forms.UserForm;
import knezija.models.forms.UserLoginForm;
import knezija.services.IUserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserManagerController {
	@Autowired
	private IUserManager userManager;

	@RequestMapping("/users/login-view")
	public String displayLoginView(Map<String, Object> model) {
		model.put("userLoginForm", new UserLoginForm());

		return "userLogin";
	}

	@RequestMapping(value = "/users/login-view/login", method = RequestMethod.POST)
	public String login(Map<String, Object> model,
			@Valid @ModelAttribute("userLoginForm") UserLoginForm form,
			BindingResult result, HttpServletRequest req) {
		boolean valid = userManager.loginUser(form, req);

		if (!valid) {
			model.put("loginFailedMessage",
					"Unijeli ste krivo korisničko ime ili lozinku!");
			return "userLogin";
		} else {
			return "redirect:/home";
		}
	}

	@RequestMapping("/users/manage")
	public String displayView(Map<String, Object> model) {
		model.put("userForm", new UserForm());

		populateManageUsersView(model);
		return "manageUsers";
	}

	@RequestMapping(value = "/users/manage/create", method = RequestMethod.POST)
	public String createUser(
			@Valid @ModelAttribute("userForm") UserForm userForm,
			BindingResult result, Map<String, Object> model) {
		if (!result.hasErrors()) {
			userManager.createUserFromForm(userForm);
			model.put("message", "Uspješno ste stvorili novog korisnika.");
		}

		populateManageUsersView(model);
		return "manageUsers";
	}

	@RequestMapping(value = "/users/manage/update", method = RequestMethod.POST)
	public String deleteOrUpdateUser(Map<String, Object> model,
			HttpServletRequest req,
			@ModelAttribute("userForm") UserForm userForm, BindingResult result) {
		String service = (String) req.getParameter("service");
		String chosenUsername = (String) req.getParameter("chosenUsername");

		if (chosenUsername != null && !chosenUsername.isEmpty()) {
			User user = userManager.findByUsername(chosenUsername);
			if (service.equals("deleteUser")) {
				userManager.deleteUser(user);

				populateManageUsersView(model);
				return "manageUsers";

			} else if (service.equals("updateData")) {
				userManager.updateUser(user);

				System.out.println(user.getId());
				return "redirect:/users/manage/update/" + user.getId();
			}
		} else {
			populateManageUsersView(model);
			return "manageUsers";
		}

		// unreachable
		return null;
	}

	@RequestMapping(value = "/users/manage/update/{id}", method = RequestMethod.GET)
	public String updateUserDisplayView(Map<String, Object> model,
			@PathVariable("id") String id) {
		long longId = Long.parseLong(id);
		User user = userManager.findUserById(longId);
		model.put("userForm", userManager.createFormFromUser(user));

		populateUpdateUserView(model, longId);
		return "updateUser";
	}

	@RequestMapping(value = "/users/manage/update/{id}/save", method = RequestMethod.POST)
	public String updateUser(
			@Valid @ModelAttribute("userForm") UserForm userForm,
			BindingResult result, Map<String, Object> model,
			@PathVariable("id") String id) {

		long longId = Long.parseLong(id);
		if (userManager.validateUpdateForm(userForm, result)) {
			userManager.updateUserFromForm(longId, userForm);
			model.put("userUpdatedMessage",
					"Uspješno ste izmijenili podatke korisnika.");
		}

		populateUpdateUserView(model, longId);
		return "updateUser";
	}

	// Utilities...

	private void populateUpdateUserView(Map<String, Object> model, long userId) {
		model.put("id", userId);
		model.put("roles", userManager.getAllRoles());
	}

	/**
	 * Populates the given view model with the attributes required by the
	 * 'manageUsers.jsp' view. Does not populate form validation attributes. Use
	 * this method prior to returning the 'manageUsers.jsp' view to the client.
	 * 
	 */
	private void populateManageUsersView(Map<String, Object> model) {
		model.put("users", userManager.getAllUsers());
		model.put("roles", userManager.getAllRoles());
	}
}
