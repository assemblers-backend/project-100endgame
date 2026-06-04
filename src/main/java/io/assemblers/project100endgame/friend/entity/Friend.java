package io.assemblers.project100endgame.friend.entity;

import io.assemblers.project100endgame.common.entity.BaseEntity;
import io.assemblers.project100endgame.user.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "from_user_id", nullable = false)
	Users fromUser;

	@ManyToOne
	@JoinColumn(name = "to_user_id", nullable = false)
	Users toUser;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	Status status = Status.PENDING;

	@Builder
	public Friend(Users fromUser, Users toUser) {
		this.fromUser = fromUser;
		this.toUser = toUser;
	}
}
