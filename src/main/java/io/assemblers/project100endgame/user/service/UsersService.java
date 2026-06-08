package io.assemblers.project100endgame.user.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.auth.config.PasswordEncoderConfig;
import io.assemblers.project100endgame.profile.entity.UserProfile;
import io.assemblers.project100endgame.profile.repository.UserProfileRepository;
import io.assemblers.project100endgame.user.dto.RegisterRequest;
import io.assemblers.project100endgame.user.dto.UsersDetails;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import io.assemblers.project100endgame.wallet.entity.Wallet;
import io.assemblers.project100endgame.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
	private final UsersRepository usersRepository;
	private final PasswordEncoderConfig passwordEncoderConfig;
	private final WalletRepository walletRepository;
	private final UserProfileRepository userProfileRepository;

	public UsersDetails loadUserById(Long id) throws UsernameNotFoundException {
		Users users = usersRepository.findById(id).orElseThrow(
			() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다.")
		);

		return UsersDetails.builder()
			.username(users.getNickname())
			.role(users.getRole())
			.build();
	}

	@Transactional
	public void registerUser(RegisterRequest request) {
		Users user = new Users(
			request.nickname(),
			passwordEncoderConfig.passwordEncoder().encode(request.password()),
			request.email(),
			"LOCAL"
		);

		usersRepository.save(user);

		Wallet wallet = Wallet.builder()
			.user(user)
			.build();

		walletRepository.save(wallet);

		UserProfile profile = UserProfile.builder()
			.user(user)
			.build();

		userProfileRepository.save(profile);
	}
}
