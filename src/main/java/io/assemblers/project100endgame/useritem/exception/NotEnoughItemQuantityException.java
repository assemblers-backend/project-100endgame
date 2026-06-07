package io.assemblers.project100endgame.useritem.exception;

public class NotEnoughItemQuantityException extends RuntimeException {
	public NotEnoughItemQuantityException() {
		super("아이템 수량이 부족합니다.");
	}
}
