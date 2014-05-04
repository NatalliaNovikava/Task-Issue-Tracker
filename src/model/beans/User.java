package model.beans;

public class User {
		
	private String email;
	private String password;
	private String lastName;
	private String firstName;
	private Role role;
	
	public User() {
		super();
		
	}
	
	public User( String email, String password, String lastName,
			String firstName, Role role) {  
		super();
		
		this.email = email;
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		this.role = role;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	public void setRole(String role) {
		this.role = Role.valueOf(role.toUpperCase());
	}

}
