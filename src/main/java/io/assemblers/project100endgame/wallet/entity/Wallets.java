package io.assemblers.project100endgame.wallet.domain;

import io.assemblers.project100endgame.common.entity.BaseEntity;
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
public class Wallets extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(unique = true, nullable = false)
	String userId;

	@Column(nullable = false)
	Long gold = 3000L;

	@Column(nullable = false)
	Long gem = 0L;

	@Builder
	public Wallets(String userId) {
		this.userId = userId;
	}
}
