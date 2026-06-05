package io.assemblers.project100endgame.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.auth.entity.RefreshToken;
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
	private final UsersRepository usersRepository;

	@Transactional
	public void tokenRotator(Long userId, String newRefreshToken) {
		Optional<Users> user = usersRepository.findById(userId);

		if (user.isEmpty()) {
			throw new RuntimeException("유저를 찾을 수 없습니다.");
		}

		RefreshToken refreshToken = tokenRepository.findByUserId(userId);

		if (refreshToken != null) {
			tokenRepository.deleteByUserId(userId);
			tokenRepository.flush();
		}

		tokenRepository.save(
				new RefreshToken(
				user.get(),
				newRefreshToken
			)
		);
	}

	@Transactional
	public void logOut(Long id) {
		tokenRepository.removeById(id);
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if ( bearerToken != null && bearerToken.startsWith("Bearer ") ) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
