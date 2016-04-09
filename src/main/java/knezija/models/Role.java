package knezija.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
public class Role {
	@GeneratedValue
	@Id
	private long id;

	@NotNull
	@Column(unique = true)
	private String roleName;

	@OneToMany(mappedBy="role")
	private List<User> allUsersOfRole;
	
	public Role(String roleName) {
		this.roleName = roleName;
	}

	public Role() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getAllUsersOfRole() {
		return allUsersOfRole;
	}

	public void setAllUsersOfRole(List<User> allUsersOfRole) {
		this.allUsersOfRole = allUsersOfRole;
	}
	
	
}
