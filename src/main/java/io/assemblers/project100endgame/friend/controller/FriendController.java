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

import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.friend.dto.FriendRequestCreateRequest;
import io.assemblers.project100endgame.friend.dto.FriendResponse;
import io.assemblers.project100endgame.friend.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/friends")
public class FriendController {

	private final FriendService friendService;
	private final TokenService tokenService;

	@GetMapping
	public ResponseEntity<GeneralResponse<List<FriendResponse>>> getFriends(HttpServletRequest request) {
		Long userId = tokenService.authValidate(request);

		return ResponseEntity.ok(
			GeneralResponse.success(null, friendService.getFriends(userId))
		);
	}

	@GetMapping("/requests")
	public ResponseEntity<GeneralResponse<List<FriendResponse>>> getReceivedFriendRequests(HttpServletRequest request) {
		Long userId = tokenService.authValidate(request);

		return ResponseEntity.ok(
			GeneralResponse.success(null, friendService.getReceivedFriendRequests(userId))
		);
	}

	@PostMapping("/requests")
	public ResponseEntity<GeneralResponse<FriendResponse>> sendFriendRequest(
		@RequestBody FriendRequestCreateRequest request,
		HttpServletRequest requestServlet
	) {
		Long userId = tokenService.authValidate(requestServlet);

		return ResponseEntity.ok(
			GeneralResponse.success("친구 요청을 보냈습니다.", friendService.sendFriendRequest(userId, request))
		);
	}

	@PostMapping("/requests/{requestId}/accept")
	public ResponseEntity<GeneralResponse<FriendResponse>> acceptFriendRequest(
		@PathVariable Long requestId,
		HttpServletRequest request
	) {
		Long userId = tokenService.authValidate(request);

		return ResponseEntity.ok(
			GeneralResponse.success(
				"친구 요청을 수락했습니다.",
				friendService.acceptFriendRequest(userId, requestId)
			)
		);
	}

	@PostMapping("/requests/{requestId}/decline")
	public ResponseEntity<GeneralResponse<Void>> declineFriendRequest(
		@PathVariable Long requestId,
		HttpServletRequest requestServlet
	) {
		Long userId = tokenService.authValidate(requestServlet);

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
		@PathVariable Long requestId,
		HttpServletRequest requestServlet
	) {
		Long userId = tokenService.authValidate(requestServlet);

		friendService.cancelFriendRequest(userId, requestId);

		return ResponseEntity.ok(
			GeneralResponse.success("친구 요청을 취소했습니다.", null)
		);
	}

	@DeleteMapping("/{friendUserId}")
	public ResponseEntity<GeneralResponse<Void>> deleteFriend(
		@PathVariable Long friendUserId,
		HttpServletRequest requestServlet
	) {
		Long userId = tokenService.authValidate(requestServlet);

		friendService.deleteFriend(userId, friendUserId);

		return ResponseEntity.ok(
			GeneralResponse.success("친구를 삭제했습니다.", null)
		);
	}
}