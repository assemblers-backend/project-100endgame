package io.assemblers.project100endgame.user.exception;

public class InvalidPasswordException extends RuntimeException {
	public InvalidPasswordException(String message) {
		super(message);
	}
}