package io.assemblers.project100endgame.wallet.entity;

import io.assemblers.project100endgame.common.entity.BaseEntity;
import io.assemblers.project100endgame.user.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wallets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", unique = true, nullable = false)
	private Users user;

	@Column(nullable = false)
	private Long gold = 3000L;

	@Column(nullable = false)
	private Long gem = 10L;

	@Builder
	public Wallet(Users user) {
		validateUser(user);

		this.user = user;
	}

	private static void validateUser(Users user) {
		if (user == null) {
			throw new IllegalArgumentException("유저는 필수입니다.");
		}
	}
}
