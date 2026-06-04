package io.assemblers.project100endgame.auth.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.auth.dto.LogInRequest;
import io.assemblers.project100endgame.auth.dto.LogInResponse;
import io.assemblers.project100endgame.auth.dto.TokenPair;
import io.assemblers.project100endgame.auth.service.TokenProvider;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.repository.UsersRepository;
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

	@PostMapping
	public ResponseEntity<GeneralResponse<LogInResponse>> login(
		@RequestBody LogInRequest logInRequest) {

		String email = logInRequest.email();
		String password = passwordEncoder.encode(logInRequest.password());

		Users user = repository.findByEmail(email);

		if (user == null || !Objects.equals(user.getPassword(), password)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(GeneralResponse.<LogInResponse>builder()
					.success(true)
					.message("이메일 또는 비밀번호가 올바르지 않습니다.")
					.data(null)
					.build()
				);
		}

		TokenPair tokenPair = tokenProvider.issueTokenPair(user.getId());

		LogInResponse logInResponse = new LogInResponse(
			tokenPair.accessToken(),
			tokenPair.refreshToken(),
			900L
		);

		return ResponseEntity.status(HttpStatus.OK)
			.body(GeneralResponse.<LogInResponse>builder()
				.success(true)
				.message("로그인되었습니다.")
				.data(logInResponse)
				.build()
		);
	}
}
