package io.assemblers.project100endgame.profile.domain;

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
public class UserProfiles extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(unique = true, nullable = false)
	Long userId;

	@Column(nullable = false)
	Long level = 1L;

	@Column(nullable = false)
	Long exp = 0L;

	@Column(nullable = false)
	Long totalPlaySeconds = 0L;

	@Builder
	public UserProfiles(Long userId) {
		this.userId = userId;
	}

}
