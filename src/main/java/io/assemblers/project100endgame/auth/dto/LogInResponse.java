package io.assemblers.project100endgame.auth.dto;

public record LogInResponse (
	String accessToken,
	String refreshToken,
	Long accessExpiresInSeconds
) {
}
