package com.dhboa.automation.framework.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User : Basic entity in any application, so is here. This is the starting
 * point of entry for any user in the application. To access the application by
 * default he must be a user in the application with appropriate role. We are
 * currently storing basic information for any user like his name, his password
 * etc., Password is stored in hash format and hence there is no way even for
 * the system to retrieve the plain text using the hash. Hence modules like
 * forgot password, change password or reset password exists.
 * 
 * @author KiranJoshi
 *
 */

@Entity
@Table(name = "af_users")
public class User {

	@Id
	@Column(name = "username", nullable = false)
	private String userName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "user_full_name", nullable = true, length = 500)
	private String userFullName;

	@Column(name = "enabled")
	private boolean isActive;

	@Column(name = "email_id", length = 500)
	private String emailId;

	@Column(name = "mobile_number", length = 10)
	private String mobileNumber;

	@Column(name = "is_notify")
	private boolean isNotificationEnabled;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true, cascade = { CascadeType.ALL })
	private List<UserRole> userRoles;
	
	public String getUserName() {
		return userName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isNotificationEnabled() {
		return isNotificationEnabled;
	}

	public void setNotificationEnabled(boolean isNotificationEnabled) {
		this.isNotificationEnabled = isNotificationEnabled;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * Somehow faster jackson is not doing what it should ideally and hence we
	 * are asking faster jackson to do a mandatory step while synchronizing the
	 * user entity. The extra effort is to manually assign roles during
	 * deserialization.
	 * 
	 * @param userRoles
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		if (this.userRoles != null)
			this.userRoles.clear();
		for (UserRole role : userRoles)
			role.setUser(this);
		this.userRoles = userRoles;
	}	
}
