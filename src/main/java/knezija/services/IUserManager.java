package knezija.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import knezija.models.Role;
import knezija.models.User;
import knezija.models.forms.UserForm;
import knezija.models.forms.UserLoginForm;

/**
 * Offers User, Role and Subscription services to the MVC layer from the
 * database layer. (adapter pattern)
 * 
 * @author Mate-1
 *
 */
@Service
public interface IUserManager {

	List<User> getAllUsers();

	List<Role> getAllRoles();

	/**
	 * Creates a new User in the system or updates an existing one. Will manage
	 * all non-managed reference field's in User.
	 * 
	 * @param fromForm
	 */
	User createUser(User fromForm);

	void deleteUser(User user);

	User findByUsername(String chosenUsername);

	User updateUser(User user);

	User findUserById(long id);

	/**
	 * Validates the user form in a way that is specific to UpdateUser
	 * operation, where the given BindingResult corresponds to the result of
	 * regular form validation. For eg., allows password field to be empty on
	 * update.
	 * 
	 * @param userForm
	 * @return
	 */
	boolean validateUpdateForm(UserForm userForm, BindingResult result);

	/**
	 * Finds and updates a user in the database from the given form. If the
	 * password field in the given form is empty, the password will not be
	 * updated. The username and id will not be updated.
	 * 
	 * @param user
	 * @param userForm
	 * @return
	 */
	User updateUserFromForm(long userId, UserForm userForm);

	Role findRoleByName(String roleName);


	/**
	 * Creates a new User in the database from the given user form.
	 * 
	 * @param userForm
	 * @return
	 */
	User createUserFromForm(UserForm userForm);

	UserForm createFormFromUser(User user);

	boolean loginUser(UserLoginForm form, HttpServletRequest req);

	/**
	 * Returns the user which is logged in.
	 * @param req
	 * @return
	 */
	User getUser(HttpServletRequest req);
}
