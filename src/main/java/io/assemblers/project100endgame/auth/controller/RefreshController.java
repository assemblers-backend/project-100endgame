package io.assemblers.project100endgame.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.auth.config.properties.JwtProperties;
import io.assemblers.project100endgame.auth.dto.RefreshRequest;
import io.assemblers.project100endgame.auth.dto.TokenPair;
import io.assemblers.project100endgame.auth.dto.TokenResponse;
import io.assemblers.project100endgame.auth.entity.RefreshToken;
import io.assemblers.project100endgame.auth.exception.InvalidRefreshTokenException;
import io.assemblers.project100endgame.auth.repository.TokenRepository;
import io.assemblers.project100endgame.auth.service.TokenProvider;
import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/refresh")
@RequiredArgsConstructor
public class RefreshController {
	private final TokenRepository tokenRepository;
	private final TokenProvider tokenProvider;
	private final TokenService tokenService;
	private final JwtProperties jwtProperties;

	@PostMapping
	public ResponseEntity<GeneralResponse<TokenResponse>> refresh(@RequestBody RefreshRequest refreshRequest) {
		String token = refreshRequest.refreshToken();
		RefreshToken refreshToken = tokenRepository.findByToken(token);

		if (refreshToken == null || !tokenProvider.validate(token) || tokenService.isTokenBan(token)) {
			throw new InvalidRefreshTokenException("유효하지 않은 Refresh Token입니다");
		}

		Long userId = refreshToken.getUser().getId();

		TokenPair newTokenPair = tokenProvider.issueTokenPair(refreshToken.getUser().getId());

		tokenService.tokenRotator(userId, newTokenPair.refreshToken());

		TokenResponse tokenResponse = new TokenResponse(
			newTokenPair.accessToken(),
			newTokenPair.refreshToken(),
			jwtProperties.getValidations().getAccess() / 1000L
		);

		return ResponseEntity.status(HttpStatus.OK)
			.body(GeneralResponse.<TokenResponse>builder()
				.success(true)
				.message(null)
				.data(tokenResponse)
				.build()
			);
	}
}
