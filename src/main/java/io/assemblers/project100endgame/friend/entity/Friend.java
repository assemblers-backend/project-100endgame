package io.assemblers.project100endgame.friend.entity;

import java.util.Objects;

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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "friend_requests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "from_user_id", nullable = false)
	private Users fromUser;

	@ManyToOne
	@JoinColumn(name = "to_user_id", nullable = false)
	private Users toUser;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FriendStatus friendStatus = FriendStatus.PENDING;

	@Builder
	public Friend(Users fromUser, Users toUser) {
		this.fromUser = Objects.requireNonNull(fromUser, "fromUser는 NULL이 될 수 없습니다.");
		this.toUser = Objects.requireNonNull(toUser, "toUser는 NULL이 될 수 없습니다.");

		if (this.fromUser == this.toUser
			|| (this.fromUser.getId() != null && this.fromUser.getId().equals(this.toUser.getId()))) {
			throw new IllegalArgumentException("자기 자신을 친구로 추가할 수 없습니다.");
		}
	}

	public void accept() {
		if (this.friendStatus != FriendStatus.PENDING) {
			throw new IllegalStateException("대기 중인 친구 요청만 수락할 수 있습니다.");
		}

		this.friendStatus = FriendStatus.ACCEPTED;
	}

	public void decline() {
		if (this.friendStatus != FriendStatus.PENDING) {
			throw new IllegalStateException("대기 중인 친구 요청만 거절할 수 있습니다.");
		}

		this.friendStatus = FriendStatus.DECLINED;
	}

	public void cancel() {
		if (this.friendStatus != FriendStatus.PENDING) {
			throw new IllegalStateException("대기 중인 친구 요청만 취소할 수 있습니다.");
		}

		this.friendStatus = FriendStatus.CANCELED;
	}
}
