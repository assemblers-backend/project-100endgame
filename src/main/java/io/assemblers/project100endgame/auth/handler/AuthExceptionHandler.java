package io.assemblers.project100endgame.auth.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.assemblers.project100endgame.auth.exception.InvalidRefreshTokenException;
import io.assemblers.project100endgame.auth.exception.LogInFailedException;
import io.assemblers.project100endgame.auth.exception.AuthFailedException;
import io.assemblers.project100endgame.common.response.GeneralResponse;

@RestControllerAdvice
public class AuthExceptionHandler {
	@ExceptionHandler(LogInFailedException.class)
	public ResponseEntity<GeneralResponse<Object>> handleLogInFailedException(LogInFailedException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(GeneralResponse.builder()
				.success(false)
				.message(e.getMessage())
				.data(null)
				.build()
			);
	}

	@ExceptionHandler(InvalidRefreshTokenException.class)
	public ResponseEntity<GeneralResponse<Object>> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(GeneralResponse.builder()
				.success(false)
				.message(e.getMessage())
				.data(null)
				.build()
			);
	}

	@ExceptionHandler(AuthFailedException.class)
	public ResponseEntity<GeneralResponse<Object>> handleLogOutFailedException(AuthFailedException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(GeneralResponse.builder()
				.success(false)
				.message(e.getMessage())
				.data(null)
				.build()
			);
	}

}
