package io.assemblers.project100endgame.friend.exception;

public class FriendRelationNotFoundException extends RuntimeException {
	public FriendRelationNotFoundException() {
		super("친구 관계가 아닙니다.");
	}
}
