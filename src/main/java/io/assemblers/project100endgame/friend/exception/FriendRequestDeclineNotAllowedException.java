package io.assemblers.project100endgame.friend.exception;

public class FriendRequestDeclineNotAllowedException extends RuntimeException {
	public FriendRequestDeclineNotAllowedException() {
		super("본인에게 온 요청만 거절할 수 있습니다.");
	}
}
