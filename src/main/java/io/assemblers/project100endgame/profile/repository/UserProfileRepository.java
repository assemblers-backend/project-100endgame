package io.assemblers.project100endgame.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.assemblers.project100endgame.profile.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

	Optional<UserProfile> findByUserId(Long userId);

}
