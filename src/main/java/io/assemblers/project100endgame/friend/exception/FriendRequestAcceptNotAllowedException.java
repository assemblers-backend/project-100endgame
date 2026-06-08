package io.assemblers.project100endgame.friend.exception;

public class FriendRequestAcceptNotAllowedException extends RuntimeException {
	public FriendRequestAcceptNotAllowedException() {
		super("본인에게 온 요청만 수락할 수 있습니다.");
	}
}
