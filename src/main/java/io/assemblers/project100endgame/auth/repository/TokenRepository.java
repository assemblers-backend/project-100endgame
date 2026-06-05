package io.assemblers.project100endgame.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.assemblers.project100endgame.auth.entity.RefreshToken;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
	RefreshToken findByToken(String token);
	RefreshToken findByUserId(Long userId);

	void deleteByUserId(Long userId);
	void removeById(Long id);
}
