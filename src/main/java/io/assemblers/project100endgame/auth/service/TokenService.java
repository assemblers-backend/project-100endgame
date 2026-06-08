package io.assemblers.project100endgame.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.auth.entity.RefreshToken;
import io.assemblers.project100endgame.auth.entity.TokenBlacklist;
import io.assemblers.project100endgame.auth.exception.AuthFailedException;
import io.assemblers.project100endgame.auth.exception.UserNotFoundException;
import io.assemblers.project100endgame.auth.repository.TokenBlacklistRepository;
import io.assemblers.project100endgame.auth.repository.TokenRepository;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
	private final TokenRepository tokenRepository;
	private final TokenBlacklistRepository tokenBlacklistRepository;
	private final UsersRepository usersRepository;
	private final TokenProvider tokenProvider;

	@Transactional
	public void tokenRotator(Long userId, String newToken) {
		Optional<Users> findUser = usersRepository.findById(userId);

		Users user = findUser.orElseThrow(() -> new UserNotFoundException(userId));

		RefreshToken refreshToken = tokenRepository.findByUserId(user.getId());

		if (refreshToken != null) {
			addTokenBlacklist(refreshToken.getToken());

			tokenRepository.deleteByUserId(userId);
			tokenRepository.flush();
		}

		tokenRepository.save(
				new RefreshToken(
				user,
				newToken
			)
		);
	}

	@Transactional
	public void logOut(Long id) {
		RefreshToken token = tokenRepository.findByUserId(id);

		if (token == null) {
			throw new UserNotFoundException(id);
		}

		tokenRepository.deleteByUserId(id);
		addTokenBlacklist(token.getToken());
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if ( bearerToken != null && bearerToken.startsWith("Bearer ") ) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public Long authValidate(HttpServletRequest request) {
		String token = resolveToken(request);

		if (token == null) {
			throw new AuthFailedException("인증이 필요합니다.");
		}

		if (!tokenProvider.validate(token)) {
			throw new AuthFailedException("유효하지 않은 토큰입니다.");
		}

		Long id = Long.valueOf(tokenProvider.parseClaims(token).getSubject());
		Boolean isAccess = claims.get("isAccess", Boolean.class);

		RefreshToken refreshToken = tokenRepository.findByUserId(id);

		if (refreshToken == null || !isAccess || isTokenBan(refreshToken.getToken())) {
			throw new AuthFailedException("유효하지 않은 토큰입니다.");
		}

		return id;
	}

	public boolean isTokenBan(String token) {
		return tokenBlacklistRepository.findByRefreshToken(token) != null;
	}

	@Transactional
	public void addTokenBlacklist(String token) {
		tokenBlacklistRepository.save(
			TokenBlacklist.builder()
				.refreshToken(token)
				.build()
		);
	}
}
