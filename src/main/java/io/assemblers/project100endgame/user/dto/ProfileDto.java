package io.assemblers.project100endgame.user.dto;

public record ProfileDto(
	Integer level,
	Long exp,
	Long totalPlaySeconds
) {

}
