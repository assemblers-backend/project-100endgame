package io.assemblers.project100endgame.friend.exception;

public class DuplicateFriendRequestException extends RuntimeException {
	public DuplicateFriendRequestException() {
		super("이미 친구 관계입니다. / 이미 보낸 친구 요청이 있습니다.");
	}
}
