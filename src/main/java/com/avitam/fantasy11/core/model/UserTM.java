package com.avitam.fantasy11.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserTM extends CommonFields {

	private String email;
	private String referredBy;
	private String username;
	private String password;
	private String organization;

	@Transient
	private String passwordConfirm;

	private Locale locale;

	@ManyToMany
	private Set<Role> roles;

	private String resetPasswordToken;

	public String getRoleNames() {
		StringBuffer buffer = new StringBuffer();
		for (Role role : roles) {
			buffer.append(role.getName() + " ");
		}
		return buffer.toString();
	}
}
