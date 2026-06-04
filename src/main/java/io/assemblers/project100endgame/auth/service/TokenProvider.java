package io.assemblers.project100endgame.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.assemblers.project100endgame.auth.config.properties.JwtProperties;
import io.assemblers.project100endgame.auth.dto.TokenPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {
	private final JwtProperties jwtProperties;

	public TokenPair issueTokenPair(Long id) {
		String accessToken = issueAccessToken(id);
		String refreshToken = issueRefreshToken(id);

		return new TokenPair(accessToken, refreshToken);
	}

	private String issue(Long id, Long validTime) {
		return Jwts.builder()
			.subject(id.toString())
			.issuedAt(new Date())
			.expiration(new Date(new Date().getTime() + validTime))
			.signWith(getSecretKey())
			.compact();
	}

	public String issueAccessToken(Long id) {
		return issue(id, jwtProperties.getValidations().getAccess());
	}

	public String issueRefreshToken(Long id) {
		return issue(id, jwtProperties.getValidations().getRefresh());
	}

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecrets().getAppKey().getBytes());
	}

	public boolean validate(String token) {
		try {
			parseId(token);
			return true;
		} catch ( JwtException e ) {
			log.error("Token validation failed: {}", e.getMessage());
		} catch ( IllegalStateException e ) {
			log.error("Illegal state during token validation");
		} catch ( Exception e ) {
			log.error("Unexpected error during token validation: {}", e.getMessage());
		}

		return false;
	}

	public Long parseId(String token) {
		return Long.parseLong(
			Jwts.parser()
			.verifyWith(getSecretKey())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject()
		);
	}
}
