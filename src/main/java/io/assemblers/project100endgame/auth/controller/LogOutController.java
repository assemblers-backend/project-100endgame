package io.assemblers.project100endgame.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.auth.dto.TokenPair;
import io.assemblers.project100endgame.auth.exception.LogOutFailedException;
import io.assemblers.project100endgame.auth.repository.TokenRepository;
import io.assemblers.project100endgame.auth.service.TokenProvider;
import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/logout")
@RequiredArgsConstructor
public class LogOutController {
	private final TokenService tokenService;
	private final TokenProvider tokenProvider;
	private final TokenRepository tokenRepository;

	@PostMapping
	public ResponseEntity<GeneralResponse<Object>> logOut(HttpServletRequest request) {
		String token = tokenService.resolveToken(request);

		if (token == null) {
			throw new LogOutFailedException("인증이 필요합니다.");
		}

		Long id = tokenProvider.parseId(token);

		tokenService.logOut(id);

		return ResponseEntity.status(HttpStatus.OK)
			.body(GeneralResponse.builder()
				.success(true)
				.message("로그아웃되었습니다.")
				.data(null)
				.build()
		);
	}
}
