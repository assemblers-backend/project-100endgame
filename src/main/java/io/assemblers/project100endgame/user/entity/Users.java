package io.assemblers.project100endgame.user.entity;

import java.time.LocalDateTime;

import io.assemblers.project100endgame.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String nickname;

	@Setter
	@Column(nullable = false)
	private String role = "USER";

	@Setter
	@Column(nullable = false)
	private String status = "ACTIVE";

	@Column(nullable = false)
	private String provider;

	@Setter
	private String profileImageUrl = null;

	@Setter
	private LocalDateTime lastLogInAt = null;

	@Builder
	public Users(String nickname, String password, String email, String provider) {
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.provider = provider;
	}
}
