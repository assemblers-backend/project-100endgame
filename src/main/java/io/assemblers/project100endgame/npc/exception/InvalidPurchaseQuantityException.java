package io.assemblers.project100endgame.npc.exception;

public class InvalidPurchaseQuantityException extends RuntimeException {
	public InvalidPurchaseQuantityException() {
		super("구매 수량은 1 이상이어야 합니다.");
	}
}
