package io.assemblers.project100endgame.user.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.assemblers.project100endgame.user.dto.UsersDetails;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
	private final UsersRepository usersRepository;

	public UsersDetails loadUserById(Long id) throws UsernameNotFoundException {
		Users users = usersRepository.findById(id).orElseThrow(
			() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다.")
		);

		return UsersDetails.builder()
			.username(users.getNickname())
			.role(users.getRole())
			.build();
	}
}
