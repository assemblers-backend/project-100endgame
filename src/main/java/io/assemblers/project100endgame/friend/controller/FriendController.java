package io.assemblers.project100endgame.friend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.friend.dto.FriendRequestCreateRequest;
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

	@PostMapping("/requests")
	public ResponseEntity<GeneralResponse<FriendResponse>> sendFriendRequest(
		@RequestBody FriendRequestCreateRequest request
	) {
		Long userId = 5L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		return ResponseEntity.ok(
			GeneralResponse.success("친구 요청을 보냈습니다.", friendService.sendFriendRequest(userId, request))
		);
	}

	@PostMapping("/requests/{requestId}/accept")
	public ResponseEntity<GeneralResponse<FriendResponse>> acceptFriendRequest(
		@PathVariable Long requestId
	) {
		Long userId = 1L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		return ResponseEntity.ok(
			GeneralResponse.success(
				"친구 요청을 수락했습니다.",
				friendService.acceptFriendRequest(userId, requestId)
			)
		);
	}

	@PostMapping("/requests/{requestId}/decline")
	public ResponseEntity<GeneralResponse<Void>> declineFriendRequest(
		@PathVariable Long requestId
	) {
		Long userId = 2L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		friendService.declineFriendRequest(userId, requestId);

		return ResponseEntity.ok(
			GeneralResponse.success(
				"친구 요청을 거절했습니다.",
				null
			)
		);
	}

	@DeleteMapping("/requests/{requestId}")
	public ResponseEntity<GeneralResponse<Void>> cancelFriendRequest(
		@PathVariable Long requestId
	) {
		Long userId = 4L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		friendService.cancelFriendRequest(userId, requestId);

		return ResponseEntity.ok(
			GeneralResponse.success("친구 요청을 취소했습니다.", null)
		);
	}
}