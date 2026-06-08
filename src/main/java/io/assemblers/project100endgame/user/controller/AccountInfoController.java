package io.assemblers.project100endgame.user.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.auth.exception.UserNotFoundException;
import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.user.dto.UserResponse;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/me")
@RequiredArgsConstructor
public class AccountInfoController {
	private final TokenService tokenService;
	private final UsersRepository usersRepository;

	@GetMapping
	public ResponseEntity<GeneralResponse<UserResponse>> accountInfo(HttpServletRequest request) {
		Long id = tokenService.authValidate(request);
		Optional<Users> byId = usersRepository.findById(id);

		if (byId.isEmpty()) {
			throw new UserNotFoundException(id);
		}

		Users user = byId.get();

		UserResponse userResponse = new UserResponse(
			user.getId(),
			user.getEmail(),
			user.getNickname(),
			user.getRole(),
			user.getProvider(),
			user.getProvider(),
			user.getProfileImageUrl(),
			user.getCreatedAt().toString(),
			user.getLastLogInAt().toString()
		);

		return ResponseEntity.status(HttpStatus.OK)
			.body(GeneralResponse.<UserResponse>builder()
				.success(true)
				.msg(null)
				.data(userResponse)
				.build()
			);
	}
}
