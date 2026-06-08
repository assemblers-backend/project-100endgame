package io.assemblers.project100endgame.friend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.friend.dto.FriendResponse;
import io.assemblers.project100endgame.friend.service.FriendService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/friends")
public class FriendController {

	private final FriendService friendService;

	@GetMapping
	public ResponseEntity<GeneralResponse<List<FriendResponse>>> getFriends() {
		Long userId = 1L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		return ResponseEntity.ok(
			GeneralResponse.success(null, friendService.getFriends(userId))
		);
	}

	@GetMapping("/requests")
	public ResponseEntity<GeneralResponse<List<FriendResponse>>> getReceivedFriendRequests() {
		Long userId = 1L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		return ResponseEntity.ok(
			GeneralResponse.success(null, friendService.getReceivedFriendRequests(userId))
		);
	}
}