package io.assemblers.project100endgame.user.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.user.exception.ExistEmailException;
import io.assemblers.project100endgame.user.exception.InvalidPasswordException;

@RestControllerAdvice
public class UsersExceptionHandler {
	@ExceptionHandler(ExistEmailException.class)
	public ResponseEntity<GeneralResponse<Object>> handleExistEmailException(ExistEmailException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
			.body(GeneralResponse.builder()
				.success(false)
				.message(e.getMessage())
				.data(null)
				.build()
			);
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<GeneralResponse<Object>> handleInvalidPasswordException(InvalidPasswordException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(GeneralResponse.builder()
				.success(false)
				.message(e.getMessage())
				.data(null)
				.build()
			);
	}

}
