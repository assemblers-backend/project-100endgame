package io.assemblers.project100endgame.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.auth.exception.UserNotFoundException;
import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.friend.entity.Friend;
import io.assemblers.project100endgame.friend.entity.FriendStatus;
import io.assemblers.project100endgame.friend.repository.FriendRepository;
import io.assemblers.project100endgame.profile.entity.UserProfile;
import io.assemblers.project100endgame.profile.exception.ProfileNotFoundException;
import io.assemblers.project100endgame.profile.repository.UserProfileRepository;
import io.assemblers.project100endgame.user.dto.ProfileDto;
import io.assemblers.project100endgame.user.dto.UserEntireInfoResponse;
import io.assemblers.project100endgame.user.dto.UserResponse;
import io.assemblers.project100endgame.user.dto.WalletDto;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import io.assemblers.project100endgame.useritem.dto.UserItemResponse;
import io.assemblers.project100endgame.useritem.repository.UserItemRepository;
import io.assemblers.project100endgame.useritem.service.UserItemService;
import io.assemblers.project100endgame.wallet.entity.Wallet;
import io.assemblers.project100endgame.wallet.exception.WalletNotFoundException;
import io.assemblers.project100endgame.wallet.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/me/data")
@RequiredArgsConstructor
public class UserEntireInfoController {
	private final TokenService tokenService;
	private final UsersRepository usersRepository;
	private final UserItemService userItemService;
	private final WalletRepository walletRepository;
	private final UserProfileRepository userProfileRepository;
	private final FriendRepository friendRepository;

	@GetMapping
	public ResponseEntity<GeneralResponse<UserEntireInfoResponse>> userEntireInfo(HttpServletRequest request) {
		Long id = tokenService.authValidate(request);
		Optional<Users> byId = usersRepository.findById(id);

		if (byId.isEmpty()) {
			throw new UserNotFoundException(id);
		}

		Users user = byId.get();
		Wallet wallet = walletRepository.findByUserId(id).orElseThrow(WalletNotFoundException::new);
		UserProfile profile = userProfileRepository.findByUserId(id).orElseThrow(ProfileNotFoundException::new);
		List<UserItemResponse> userItemResponse = userItemService.getInventory(id);

		UserResponse userResponse = new UserResponse(
			user.getId(),
			user.getEmail(),
			user.getNickname(),
			user.getRole(),
			user.getProvider(),
			user.getProvider(),
			user.getProfileImageUrl(),
			user.getCreatedAt().toString(),
			user.getLastLogInAt().toString()
		);

		ProfileDto profileDto = new ProfileDto(profile.getLevel(), profile.getExp(), profile.getTotalPlaySeconds());
		WalletDto walletDto = new WalletDto(wallet.getGold(), wallet.getGem());

		Long friendCount = (long)friendRepository.findFriendsByUserIdAndStatus(id, FriendStatus.ACCEPTED).size();

		UserEntireInfoResponse response = new UserEntireInfoResponse(
			userResponse,
			profileDto,
			walletDto,
			userItemResponse,
			friendCount
		);

		return ResponseEntity.status(HttpStatus.OK)
			.body(GeneralResponse.<UserEntireInfoResponse>builder()
				.success(true)
				.message("가입되었습니다.")
				.data(response)
				.build()
			);
	}
}
