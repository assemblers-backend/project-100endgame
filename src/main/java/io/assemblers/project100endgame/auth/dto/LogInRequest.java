package io.assemblers.project100endgame.auth.dto;

public record LogInRequest(
	String email,
	String password
) {
}
