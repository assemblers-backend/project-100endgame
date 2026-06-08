package io.assemblers.project100endgame.wallet.exception;

public class InsufficientGoldException extends RuntimeException {

	public InsufficientGoldException() {
		super("골드가 부족합니다.");
	}
}
