package knezija.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import knezija.models.Role;
import knezija.models.User;
import knezija.models.forms.UserForm;
import knezija.models.forms.UserLoginForm;
import knezija.persistence.IDao;
import knezija.utilities.PasswordHash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

public class UserManager implements IUserManager {
	@Autowired
	private IDao dao;

	@Override
	public List<User> getAllUsers() {
		return dao.findAll(User.class);
	}

	@Override
	public List<Role> getAllRoles() {
		return dao.findAll(Role.class);
	}

	@Override
	public User createUser(User user) {
		user.setRole(dao.update(user.getRole()));
		return dao.update(user);
	}

	@Override
	public void deleteUser(User user) {
		dao.remove(user);
	}

	@Override
	public User updateUser(User user) {
		return dao.update(user);
	}

	@Override
	public User findByUsername(String username) {
		return dao.find(User.class, "username", username);
	}

	@Override
	public User findUserById(long id) {
		return dao.findById(User.class, id);
	}

	@Override
	public boolean validateUpdateForm(UserForm userForm, BindingResult result) {
		return !result.hasErrors()
				|| (result.getErrorCount() == 1 && userForm.getPassword()
						.isEmpty());
	}

	@Override
	public User updateUserFromForm(long userId, UserForm form) {
		User user = dao.findById(User.class, userId);
		user.setDescription(form.getDescription());
		user.setFirstName(form.getFirstName());

		Role role = findRoleByName(form.getRole());
		role = dao.update(role);

		user.setRole(role);
		user.setTitle(form.getTitle());

		if (!form.getPassword().isEmpty()) {
			user.setPassword(form.getPassword());
		}

		return user;
	}

	@Override
	public Role findRoleByName(String roleName) {
		return dao.find(Role.class, "roleName", roleName);
	}

	@Override
	public User createUserFromForm(UserForm userForm) {
		Role role = findRoleByName(userForm.getRole());
		User user = new User(userForm.getUsername(), userForm.getPassword(),
				userForm.getFirstName(), userForm.getLastName(),
				userForm.getTitle(), userForm.getDescription(), role);

		return createUser(user);
	}

	@Override
	public UserForm createFormFromUser(User user) {
		return new UserForm(user.getUsername(), user.getFirstName(),
				user.getLastName(), user.getTitle(), user.getDescription(),
				user.getRole().getRoleName());
	}

	@Override
	public boolean loginUser(UserLoginForm form, HttpServletRequest req) {
		User user = dao.find(User.class, "username", form.getUsername());
		if (user != null) {
			if (PasswordHash.validatePassword(form.getPassword(),
					user.getPasswordHash())) {
				req.getSession().setAttribute("user", user);
				return true;
			}
		}

		return false;
	}

	@Override
	public User getUser(HttpServletRequest req) {
		return (User) req.getSession().getAttribute("user");
	}
}
