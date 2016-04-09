package knezija.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import knezija.services.IUserManager;
import knezija.utilities.PasswordHash;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class User {
	@Autowired
	private static IUserManager manager;
	
	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private String username;

	@Column
	private String passwordHash;

	@Column
	private String firstName;

	@Column
	private String lastName;

	/**
	 * Titula.
	 */
	@Column
	private String title;

	@Column
	private String description;

	@ManyToOne
	@JoinColumn
	private Role role;

	public User(String username, String password, String firstName,
			String lastName, String title, String description, Role role) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.description = description;
		this.role = role;

		setPassword(password);
	}

	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setPassword(String password) {
			passwordHash = PasswordHash.createHash(password);
	}
}
