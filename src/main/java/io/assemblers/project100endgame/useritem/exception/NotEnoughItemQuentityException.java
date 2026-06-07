package io.assemblers.project100endgame.useritem.exception;

public class NotEnoughItemQuentityException extends RuntimeException {
	public NotEnoughItemQuentityException() {
		super("아이템 수량이 부족합니다.");
	}
}
