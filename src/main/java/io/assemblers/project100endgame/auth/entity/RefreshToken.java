package io.assemblers.project100endgame.auth.entity;

import java.time.LocalDateTime;

import io.assemblers.project100endgame.common.entity.BaseEntity;
import io.assemblers.project100endgame.user.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@OneToOne
	@JoinColumn(nullable = false)
	Users user;

	@Column(unique = true, nullable = false, length = 255)
	String token;

	@Column(nullable = false)
	LocalDateTime expiredAt;

	@Builder
	public RefreshToken(Users user, String token) {
		this.user = user;
		this.token = token;
	}

	@PrePersist
	protected void onCreate() {
		if (this.expiredAt == null) {
			this.expiredAt = LocalDateTime.now().plusDays(14);
		}
	}
}
