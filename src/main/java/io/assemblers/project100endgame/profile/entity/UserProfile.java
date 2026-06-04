package io.assemblers.project100endgame.profile.entity;

import io.assemblers.project100endgame.common.entity.BaseEntity;
import io.assemblers.project100endgame.user.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@OneToOne
	@JoinColumn(name = "users", unique = true, nullable = false)
	Users user;

	@Column(nullable = false)
	Long level = 1L;

	@Column(nullable = false)
	Long exp = 0L;

	@Column(nullable = false)
	Long totalPlaySeconds = 0L;

	@Builder
	public UserProfile(Users user) {
		this.user = user;
	}

}
