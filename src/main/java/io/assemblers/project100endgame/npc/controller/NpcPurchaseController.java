package io.assemblers.project100endgame.npc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.npc.dto.NpcItemPurchaseRequest;
import io.assemblers.project100endgame.npc.dto.NpcItemPurchaseResponse;
import io.assemblers.project100endgame.npc.service.NpcPurchaseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/npcs")
public class NpcPurchaseController {

	private final NpcPurchaseService npcPurchaseService;

	@PostMapping("/{npcId}/items/{npcItemId}/purchase")
	public ResponseEntity<GeneralResponse<NpcItemPurchaseResponse>> purchaseItem(
		@PathVariable Long npcId,
		@PathVariable Long npcItemId,
		@RequestBody NpcItemPurchaseRequest request
	) {
		Long userId = 1L; // TODO: 인증 구현 후 로그인 유저 ID로 교체

		return ResponseEntity.ok(
			GeneralResponse.success(
				"구매가 완료되었습니다.",
				npcPurchaseService.purchaseItem(userId, npcId, npcItemId, request)
			)
		);
	}
}
