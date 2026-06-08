package io.assemblers.project100endgame.profile.dto;

import io.assemblers.project100endgame.profile.entity.UserProfile;

public record ProfileResponse(
	Long profileId,
	Integer level,
	Long exp,
	Long totalPlaySeconds
) {

	public static ProfileResponse from(UserProfile profile) {
		return new ProfileResponse(
			profile.getId(),
			profile.getLevel(),
			profile.getExp(),
			profile.getTotalPlaySeconds()
		);
	}
}
