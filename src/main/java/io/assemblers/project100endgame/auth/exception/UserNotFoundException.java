package io.assemblers.project100endgame.auth.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(Long userId) {
		log.warn("유저를 찾을 수 없습니다. {}", userId);
	}
}
