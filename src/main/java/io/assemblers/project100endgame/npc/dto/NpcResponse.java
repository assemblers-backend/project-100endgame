package io.assemblers.project100endgame.npc.dto;

import java.util.List;

import io.assemblers.project100endgame.npc.entity.Npc;

public record NpcResponse(
	Long npcId,
	String rId,
	String name,
	String description,
	String locationKey,
	boolean active,
	List<NpcShopItemResponse> shopItems
) {
	public static NpcResponse from(Npc npc) {
		return new NpcResponse(
			npc.getId(),
			npc.getRId(),
			npc.getName(),
			npc.getDescription(),
			npc.getLocationKey(),
			npc.isActive(),
			npc.getNpcItems()
				.stream()
				.map(NpcShopItemResponse::from)
				.toList()
		);
	}
}
