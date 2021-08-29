package com.tecnotac.ecomerce.model;

import java.util.Iterator;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String firstName;
	
	private String lastName;
	
	@Column(nullable = false, unique = true)
	@NotEmpty
	@Email(message = "{errors.invalid_email}")
	private String email;
	
	private String password;
	private String picture;
	private boolean enabled;
	
	@Enumerated(EnumType.STRING)
	private Provider provider;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
				name = "user_role",
				joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
						inverseJoinColumns = {@JoinColumn (name = "ROLE_ID", referencedColumnName = "ID")}
	)
	
	private List<Role> roles;
	
    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = this.roles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
         
        return false;
    }
	
	public User() {
		
	}

	public User(User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.picture = user.getPicture();
		this.enabled = user.isEnabled();
		this.provider = user.getProvider();
		this.roles = user.getRoles();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
