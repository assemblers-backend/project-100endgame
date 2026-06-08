package io.assemblers.project100endgame.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.profile.dto.ProfileResponse;
import io.assemblers.project100endgame.profile.entity.UserProfile;
import io.assemblers.project100endgame.profile.exception.ProfileNotFoundException;
import io.assemblers.project100endgame.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProfileService {

	private final UserProfileRepository userprofileResponse;

	public ProfileResponse getProfile(Long userId) {
		UserProfile userProfile = userprofileResponse.findByUserId(userId)
			.orElseThrow(ProfileNotFoundException::new);

		return ProfileResponse.from(userProfile);
	}
}
