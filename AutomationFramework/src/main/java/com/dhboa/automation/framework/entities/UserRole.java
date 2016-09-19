package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * UserRole : A role table for every user. Basically application is considering
 * only three types of roles currently i.e., USER, ADMIN, ANALYST with various
 * access abilities at application level. This table holds the information about
 * roles of a particular user. A user can have multiple roles i.e., I can be
 * both ADMIN, ANALYST and as well as a USER. By default every user has a role
 * USER.
 * 
 * @author KiranJoshi
 *
 */
@Entity
@Table(name = "af_user_roles")
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "role", nullable = false, length = 500)
	private String role;

	@ManyToOne
	@JoinColumn(name = "username", nullable = false, updatable = true, insertable = true)
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getId() {
		return id;
	}

}
