package io.assemblers.project100endgame.user.dto;

public record RegisterResponse(
	Long userId,
	String email,
	String nickname,
	String role,
	String status,
	String provider,
	String profileImageUrl,
	String createdAt,
	String lastLoginAt
) {}
