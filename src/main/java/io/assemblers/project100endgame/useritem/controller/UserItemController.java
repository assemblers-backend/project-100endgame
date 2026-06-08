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

import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.useritem.dto.ItemPickupRequest;
import io.assemblers.project100endgame.useritem.dto.UserItemResponse;
import io.assemblers.project100endgame.useritem.service.UserItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/inventory")
public class UserItemController {

	private final UserItemService userItemService;
	private final TokenService tokenService;

	@GetMapping
	public ResponseEntity<GeneralResponse<List<UserItemResponse>>> getInventory(HttpServletRequest requestServlet) {
		Long userId = tokenService.authValidate(requestServlet);

		return ResponseEntity.ok(
			GeneralResponse.success("인벤토리를 조회했습니다.", userItemService.getInventory(userId))
		);
	}

	@PostMapping("/pickup")
	public ResponseEntity<GeneralResponse<UserItemResponse>> pickupItem(
		@RequestBody ItemPickupRequest request,
		HttpServletRequest requestServlet
	) {
		Long userId = tokenService.authValidate(requestServlet);

		return ResponseEntity.ok(
			GeneralResponse.success("아이템을 획득했습니다.", userItemService.pickupItem(userId, request))
		);
	}

	@DeleteMapping("/{itemId}/discard")
	public ResponseEntity<GeneralResponse<Void>> discardItem(
		@PathVariable Long itemId,
		@RequestParam Long quantity,
		HttpServletRequest requestServlet
	) {
		Long userId = tokenService.authValidate(requestServlet);

		userItemService.discardItem(userId, itemId, quantity);

		return ResponseEntity.ok(
			GeneralResponse.success("아이템을 버렸습니다.", null)
		);
	}
}
