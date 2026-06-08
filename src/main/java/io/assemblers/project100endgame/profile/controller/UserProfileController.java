package io.assemblers.project100endgame.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.profile.dto.ProfileResponse;
import io.assemblers.project100endgame.profile.service.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/profile")
public class UserProfileController {

	private final UserProfileService userProfileService;
	private final TokenService tokenService;

	@GetMapping
	public ResponseEntity<GeneralResponse<ProfileResponse>> getProfile(HttpServletRequest requestServlet) {
		Long userId = tokenService.authValidate(requestServlet);
		/*
		public ResponseEntity<ApiResponse<List<UserItemResponse>>> getInventory(
        @AuthenticationPrincipal CustomUserDetails userDetails
) {
    Long userId = userDetails.getUserId();
		 */

		return ResponseEntity.ok(
			GeneralResponse.success("프로필을 조회했습니다.", userProfileService.getProfile(userId))
		);
	}
}
