package io.assemblers.project100endgame.user.dto;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UsersDetails implements UserDetails {
	private String username;
	private String role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.role));
	}

	@Override
	public @Nullable String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Builder
	public UsersDetails(String username, String role) {
		this.username = username;
		this.role = role;
	}
}
