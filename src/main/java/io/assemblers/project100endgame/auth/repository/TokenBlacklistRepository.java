package io.assemblers.project100endgame.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.assemblers.project100endgame.auth.entity.TokenBlacklist;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
	TokenRepository findByAccessToken(String accessToken);
}
