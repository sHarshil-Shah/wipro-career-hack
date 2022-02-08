package com.harshil.security.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.harshil.models.User;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private User user;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(User user, Collection<? extends GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {

		GrantedAuthority auth = new SimpleGrantedAuthority(user.getRole().getName().name());

		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(auth);

		return new UserDetailsImpl(user, authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		return Objects.equals(((User) o).getId(), user.getId());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}
