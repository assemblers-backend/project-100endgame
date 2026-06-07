package io.assemblers.project100endgame.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.auth.entity.RefreshToken;
import io.assemblers.project100endgame.auth.exception.UserNotFoundException;
import io.assemblers.project100endgame.auth.repository.TokenBlacklistRepository;
import io.assemblers.project100endgame.auth.repository.TokenRepository;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import io.jsonwebtoken.JwtException;
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

	@Transactional
	public void tokenRotator(Long userId, String newRefreshToken) {
		Optional<Users> findUser = usersRepository.findById(userId);

		Users user = findUser.orElseThrow(() -> new UserNotFoundException(userId));

		RefreshToken refreshToken = tokenRepository.findByUserId(userId);

		if (refreshToken != null) {
			tokenRepository.deleteByUserId(userId);
			tokenRepository.flush();
		}

		tokenRepository.save(
				new RefreshToken(
				user,
				newRefreshToken
			)
		);
	}

	@Transactional
	public void logOut(Long id) {
		tokenRepository.deleteById(id);
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if ( bearerToken != null && bearerToken.startsWith("Bearer ") ) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public void accessTokenBlacklistValidate(String token) {
		if (tokenBlacklistRepository.findByAccessToken(token) != null) {
			throw new JwtException("AccessToken이 만료되었습니다.");
		}
	}
}
