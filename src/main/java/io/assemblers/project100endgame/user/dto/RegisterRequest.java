package io.assemblers.project100endgame.user.dto;

public record RegisterRequest(
	String email,
	String nickname,
	String password
){}
