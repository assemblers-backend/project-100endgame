package io.assemblers.project100endgame.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.auth.config.properties.JwtProperties;
import io.assemblers.project100endgame.auth.dto.LogInRequest;
import io.assemblers.project100endgame.auth.dto.TokenResponse;
import io.assemblers.project100endgame.auth.dto.TokenPair;
import io.assemblers.project100endgame.auth.exception.LogInFailedException;
import io.assemblers.project100endgame.auth.repository.TokenRepository;
import io.assemblers.project100endgame.auth.service.TokenProvider;
import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/login")
@RequiredArgsConstructor
public class LogInController {
	private final UsersRepository repository;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final JwtProperties jwtProperties;
	private final TokenService tokenService;
	private final TokenRepository tokenRepository;

	@PostMapping
	public ResponseEntity<GeneralResponse<TokenResponse>> login(@RequestBody LogInRequest logInRequest) {

		String email = logInRequest.email();
		String password = logInRequest.password();

		Users user = repository.findByEmail(email);

		if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
			throw new LogInFailedException("이메일 또는 비밀번호가 올바르지 않습니다.");
		}

		TokenPair NewTokenPair = tokenProvider.issueTokenPair(user.getId());

		tokenService.tokenRotator(user.getId(), NewTokenPair.refreshToken());

		TokenResponse logInResponse = new TokenResponse(
			NewTokenPair.accessToken(),
			NewTokenPair.refreshToken(),
			jwtProperties.getValidations().getAccess() / 1000L
		);

		return ResponseEntity.status(HttpStatus.OK)
			.body(GeneralResponse.<TokenResponse>builder()
				.success(true)
				.msg("로그인되었습니다.")
				.data(logInResponse)
				.build()
		);
	}
}
