package io.assemblers.project100endgame.useritem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.useritem.dto.ItemPickupRequest;
import io.assemblers.project100endgame.useritem.dto.UserItemResponse;
import io.assemblers.project100endgame.useritem.service.UserItemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/inventory")
public class UserItemController {

	private final UserItemService userItemService;

	@GetMapping
	public ResponseEntity<GeneralResponse<List<UserItemResponse>>> getInventory() {
		Long userId = 1L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		return ResponseEntity.ok(
			GeneralResponse.success("인벤토리를 조회했습니다.", userItemService.getInventory(userId))
		);
	}

	@PostMapping("/pickup")
	public ResponseEntity<GeneralResponse<UserItemResponse>> pickupItem(
		@RequestBody ItemPickupRequest request
	) {
		Long userId = 1L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		return ResponseEntity.ok(
			GeneralResponse.success("아이템을 획득했습니다.", userItemService.pickupItem(userId, request))
		);
	}

	@DeleteMapping("/{itemId}/discard")
	public ResponseEntity<GeneralResponse<Void>> discardItem(
		@PathVariable Long itemId,
		@RequestParam Long quantity
	) {
		Long userId = 1L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		userItemService.discardItem(userId, itemId, quantity);

		return ResponseEntity.ok(
			GeneralResponse.success("아이템을 버렸습니다.", null)
		);
	}
}
