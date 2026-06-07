package io.assemblers.project100endgame.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.assemblers.project100endgame.common.dto.GeneralResponse;
import io.assemblers.project100endgame.item.exception.ItemNotFoundException;
import io.assemblers.project100endgame.npc.exception.NpcNotFoundException;
import io.assemblers.project100endgame.useritem.exception.InvalidItemQuantityException;
import io.assemblers.project100endgame.useritem.exception.NotEnoughItemQuentityException;
import io.assemblers.project100endgame.useritem.exception.UserItemNotFoundException;
import io.assemblers.project100endgame.wallet.exception.WalletNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<GeneralResponse<Void>> handleItemNotFoundException(ItemNotFoundException e) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<GeneralResponse<Void>> handleException(Exception e) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(GeneralResponse.fail("서버 오류가 발생했습니다."));
	}

	@ExceptionHandler(NpcNotFoundException.class)
	public ResponseEntity<GeneralResponse<Void>> handleNpcNotFoundException(NpcNotFoundException e) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(InvalidItemQuantityException.class)
	public ResponseEntity<GeneralResponse<Void>> handleInvalidItemQuantityException(InvalidItemQuantityException e) {
		return ResponseEntity
			.badRequest()
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(UserItemNotFoundException.class)
	public ResponseEntity<GeneralResponse<Void>> handleUserItemNotFoundException(
		UserItemNotFoundException e
	) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(NotEnoughItemQuentityException.class)
	public ResponseEntity<GeneralResponse<Void>> handleNotEnoughItemQuantityException(
		NotEnoughItemQuentityException e
	) {
		return ResponseEntity
			.badRequest()
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(WalletNotFoundException.class)
	public ResponseEntity<GeneralResponse<Void>> handleWalletNotFoundException(WalletNotFoundException e) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(GeneralResponse.fail(e.getMessage()));
	}
}

