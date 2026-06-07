package io.assemblers.project100endgame.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.auth.dto.TokenResponse;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.user.dto.RegisterRequest;
import io.assemblers.project100endgame.user.dto.RegisterResponse;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.exception.ExistEmailException;
import io.assemblers.project100endgame.user.exception.InvalidPasswordException;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import io.assemblers.project100endgame.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/register")
@RequiredArgsConstructor
public class RegisterController {
	private final UsersRepository usersRepository;
	private final UsersService usersService;

	@PostMapping
	public ResponseEntity<GeneralResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) {
		String email = registerRequest.email();
		String password = registerRequest.password();
		String nickname = registerRequest.nickname();

		if (usersRepository.findByEmail(email) != null) {
			throw new ExistEmailException("이미 사용 중인 이메일입니다");
		}

		if (!(8 <= password.length() && password.length() <= 64)) {
			throw new InvalidPasswordException("비밀번호는 8~64자여야 합니다.");
		}

		usersService.registerUser(registerRequest);

		Users user = usersRepository.findByEmail(email);

		RegisterResponse registerResponse = new RegisterResponse(
			user.getId(),
			email,
			nickname,
			"USER",
			"ACTIVE",
			user.getProvider(),
			user.getProfileImageUrl(),
			user.getCreatedAt().toString(),
			null
		);

		return ResponseEntity.status(HttpStatus.OK)
			.body(GeneralResponse.<RegisterResponse>builder()
				.success(true)
				.msg("가입되었습니다.")
				.data(registerResponse)
				.build()
			);
	}

}
