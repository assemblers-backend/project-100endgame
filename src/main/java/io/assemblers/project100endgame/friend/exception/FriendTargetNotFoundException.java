package io.assemblers.project100endgame.friend.exception;

public class FriendTargetNotFoundException extends RuntimeException {
	public FriendTargetNotFoundException() {
		super("유저를 찾을 수 없습니다.");
	}
}
