package io.assemblers.project100endgame.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.assemblers.project100endgame.user.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Users findByEmail(String email);

	Users findByNickname(String nickname);
}
