package io.assemblers.project100endgame.npc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.npc.dto.NpcResponse;
import io.assemblers.project100endgame.npc.entity.Npc;
import io.assemblers.project100endgame.npc.exception.NpcNotFoundException;
import io.assemblers.project100endgame.npc.repository.NpcRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NpcService {
/*1. Repository에서 가져온 Npc 엔티티를 NpcResponse DTO로 변환한다.
2. NPC가 없으면 NpcNotFoundException을 던진다.
 */

	private final NpcRepository npcRepository;

	// 다건 조회
	public List<NpcResponse> getNpcs() {
		return npcRepository.findAllActiveWithItems()
			.stream()
			.map(NpcResponse::from)
			.toList();
	}

	// 단건 조회
	public NpcResponse getNpc(Long npcId) {
		Npc npc = npcRepository.findActiveByIdWithItems(npcId)
			.orElseThrow(NpcNotFoundException::new);

		return NpcResponse.from(npc);

	}
}


