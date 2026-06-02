package io.assemblers.project100endgame.user.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String password;
	private String nickname;

	private String role = "USER";
	@Setter
	private String status;
	private String provider;
	private String profileImageUrl = null;

	private LocalDateTime createdAt = LocalDateTime.now();

	@Setter
	private LocalDateTime updatedAt = LocalDateTime.now();

	@Setter
	private LocalDateTime lastLoginInAt = null;

	@Builder
	public Member(String username, String password, String email, String provider) {
		this.nickname = username;
		this.password = password;
		this.email = email;
		this.provider = provider;
	}
}
