package io.assemblers.project100endgame.friend.dto;

import java.time.LocalDateTime;

import io.assemblers.project100endgame.friend.entity.Friend;
import io.assemblers.project100endgame.user.entity.Users;

public record FriendResponse(
	Long friendRequestId,
	Long fromUserId,
	Long toUserId,
	String status,
	LocalDateTime createdAt,
	String nickname
) {

	public static FriendResponse friendOf(Friend friend, Long currentUserId) {

		// 내 ID가 보낸거면 to가 상대방. form이 나. 그게 아니면 to = 받는게 나.
		Users opponent = friend.getFromUser().getId().equals(currentUserId)
			? friend.getToUser()
			: friend.getFromUser();

		return new FriendResponse(
			friend.getId(),
			friend.getFromUser().getId(),
			friend.getToUser().getId(),
			friend.getFriendStatus().name(),
			friend.getCreatedAt(),
			opponent.getNickname()
		);
	}

	public static FriendResponse requestFrom(Friend friend) {
		Users requester = friend.getFromUser();

		return new FriendResponse(
			friend.getId(),
			friend.getFromUser().getId(),
			friend.getToUser().getId(),
			friend.getFriendStatus().name(),
			friend.getCreatedAt(),
			requester.getNickname()
		);
	}
}
