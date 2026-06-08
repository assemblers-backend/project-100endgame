package io.assemblers.project100endgame.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.friend.exception.DuplicateFriendRequestException;
import io.assemblers.project100endgame.friend.exception.FriendRequestAcceptNotAllowedException;
import io.assemblers.project100endgame.friend.exception.FriendRequestCancelNotAllowedException;
import io.assemblers.project100endgame.friend.exception.FriendRequestDeclineNotAllowedException;
import io.assemblers.project100endgame.friend.exception.FriendRequestNotFoundException;
import io.assemblers.project100endgame.friend.exception.FriendTargetNotFoundException;
import io.assemblers.project100endgame.friend.exception.SelfFriendRequestException;
import io.assemblers.project100endgame.item.exception.ItemNotFoundException;
import io.assemblers.project100endgame.npc.exception.NpcNotFoundException;
import io.assemblers.project100endgame.profile.exception.ProfileNotFoundException;
import io.assemblers.project100endgame.useritem.exception.InvalidItemQuantityException;
import io.assemblers.project100endgame.useritem.exception.NotEnoughItemQuantityException;
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

	@ExceptionHandler(NotEnoughItemQuantityException.class)
	public ResponseEntity<GeneralResponse<Void>> handleNotEnoughItemQuantityException(
		NotEnoughItemQuantityException e
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

	@ExceptionHandler(ProfileNotFoundException.class)
	public ResponseEntity<GeneralResponse<Void>> handleProfileNotFoundException(ProfileNotFoundException e) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(FriendTargetNotFoundException.class)
	public ResponseEntity<GeneralResponse<Void>> handleFriendTargetNotFoundException(
		FriendTargetNotFoundException e
	) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(SelfFriendRequestException.class)
	public ResponseEntity<GeneralResponse<Void>> handleSelfFriendRequestException(
		SelfFriendRequestException e
	) {
		return ResponseEntity
			.badRequest()
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(DuplicateFriendRequestException.class)
	public ResponseEntity<GeneralResponse<Void>> handleDuplicateFriendRequestException(
		DuplicateFriendRequestException e
	) {
		return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(FriendRequestNotFoundException.class)
	public ResponseEntity<GeneralResponse<Void>> handleFriendRequestNotFoundException(
		FriendRequestNotFoundException e
	) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(FriendRequestAcceptNotAllowedException.class)
	public ResponseEntity<GeneralResponse<Void>> handleFriendRequestAcceptNotAllowedException(
		FriendRequestAcceptNotAllowedException e
	) {
		return ResponseEntity
			.badRequest()
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(FriendRequestDeclineNotAllowedException.class)
	public ResponseEntity<GeneralResponse<Void>> handleFriendRequestDeclineNotAllowedException(
		FriendRequestDeclineNotAllowedException e
	) {
		return ResponseEntity
			.badRequest()
			.body(GeneralResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(FriendRequestCancelNotAllowedException.class)
	public ResponseEntity<GeneralResponse<Void>> handleFriendRequestCancelNotAllowedException(
		FriendRequestCancelNotAllowedException e
	) {
		return ResponseEntity
			.badRequest()
			.body(GeneralResponse.fail(e.getMessage()));
	}
}

