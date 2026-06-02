package io.assemblers.project100endgame.auth.domain;

import java.time.LocalDateTime;

import io.assemblers.project100endgame.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokens extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(unique = true, nullable = false)
	Long userId;

	@Column(unique = true, nullable = false)
	String token;

	LocalDateTime expiredAt;

	@Builder
	public RefreshTokens(Long userId, String token) {
		this.userId = userId;
		this.token = token;

		this.expiredAt = createdAt.plusDays(14);
	}
}
