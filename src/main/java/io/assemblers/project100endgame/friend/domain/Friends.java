package io.assemblers.project100endgame.friend.domain;

import io.assemblers.project100endgame.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Friends extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false)
	Long fromUserId;

	@Column(nullable = false)
	Long toUserId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	Status status = Status.PENDING;

	@Builder
	public Friends(Long fromUserId, Long toUserId) {
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
	}
}
