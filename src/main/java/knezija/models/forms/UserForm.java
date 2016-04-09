package knezija.models.forms;

import knezija.models.forms.validation.annotations.Password;

import org.hibernate.validator.constraints.NotEmpty;

public class UserForm {
	@NotEmpty(message = "Morate unijeti korisničko ime.")
	private String username;

	@Password(message="Lozinka mora biti duga bar 6 znakova.")
	private String password;
	private String firstName;
	private String lastName;
	private String title;
	private String description;
	
	@NotEmpty(message = "Morate odabrati pravo pristupa.")
	private String role;

	public UserForm() {
		super();
	}

	public UserForm(String username, String firstName,
			String lastName, String title, String description, String role) {
		super();
		this.username = username;
		this.password = "";
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.description = description;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
