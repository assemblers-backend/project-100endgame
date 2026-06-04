package io.assemblers.project100endgame.auth.dto;

public record TokenPair(
	String accessToken,
	String refreshToken
) {
}
