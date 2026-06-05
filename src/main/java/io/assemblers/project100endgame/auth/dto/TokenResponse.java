package io.assemblers.project100endgame.auth.dto;

public record TokenResponse (
	String accessToken,
	String refreshToken,
	Long accessExpiresInSeconds
) {
}
