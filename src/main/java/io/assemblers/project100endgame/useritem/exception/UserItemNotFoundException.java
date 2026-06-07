package io.assemblers.project100endgame.useritem.exception;

public class UserItemNotFoundException extends RuntimeException {

	public UserItemNotFoundException() {
		super("아이템을 찾을 수 없습니다.");
	}
}
