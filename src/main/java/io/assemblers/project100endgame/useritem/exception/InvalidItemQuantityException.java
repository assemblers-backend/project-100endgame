package io.assemblers.project100endgame.useritem.exception;

public class InvalidItemQuantityException extends RuntimeException {

	public InvalidItemQuantityException() {
		super("수량은 1 이상이어야 합니다.");
	}
}
