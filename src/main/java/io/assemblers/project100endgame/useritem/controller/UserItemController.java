package io.assemblers.project100endgame.useritem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.common.dto.GeneralResponse;
import io.assemblers.project100endgame.useritem.dto.UserItemResponse;
import io.assemblers.project100endgame.useritem.service.UserItemService;
import lombok.RequiredArgsConstructor;

@RestController("/api/v1/users/me/inventory")
@RequiredArgsConstructor
public class UserItemController {

	private final UserItemService userItemService;

	@GetMapping
	public ResponseEntity<GeneralResponse<List<UserItemResponse>>> getInventory() {
		Long userId = 1L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		return ResponseEntity.ok(
			GeneralResponse.success("인벤토리를 조회했습니다.", userItemService.getInventory(userId))
		);
	}
}
