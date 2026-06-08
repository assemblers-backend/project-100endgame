package io.assemblers.project100endgame.npc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.common.response.ApiResponse;
import io.assemblers.project100endgame.npc.dto.NpcResponse;
import io.assemblers.project100endgame.npc.service.NpcService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/npcs")
public class NpcController {

	private final NpcService npcService;

	@GetMapping
	public ResponseEntity<GeneralResponse<List<NpcResponse>>> getNpcs() {
		return ResponseEntity.ok(
			GeneralResponse.success("NPC 목록을 조회했습니다.", npcService.getNpcs())
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GeneralResponse<NpcResponse>> getNpc(@PathVariable Long id){
		return ResponseEntity.ok(
			GeneralResponse.success("NPC를 조회했습니다.", npcService.getNpc(id))
		);
	}

}
