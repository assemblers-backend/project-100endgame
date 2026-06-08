package io.assemblers.project100endgame.friend.exception;

public class FriendRequestCancelNotAllowedException extends RuntimeException {
	public FriendRequestCancelNotAllowedException() {
		super("본인이 보낸 요청만 취소할 수 있습니다.");
	}
}
