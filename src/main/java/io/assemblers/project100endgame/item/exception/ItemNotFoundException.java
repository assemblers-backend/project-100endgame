package io.assemblers.project100endgame.item.exception;

public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException() {
		super("아이템을 찾을 수 없습니다.");
	}
}