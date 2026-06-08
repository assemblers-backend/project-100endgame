package io.assemblers.project100endgame.npc.dto;

import io.assemblers.project100endgame.item.entity.Item;
import io.assemblers.project100endgame.npc.entity.NpcItem;

public record NpcShopItemResponse(
	Long npcItemId,
	Long itemId,
	String rId,
	String itemName,
	String itemType,
	String itemGrade,
	String description,
	Long price,
	Long sellPrice,
	Long quantity,
	Long sortOrder
) {
	public static NpcShopItemResponse from(NpcItem npcItem) {
		Item item = npcItem.getItem();

		return new NpcShopItemResponse(
			npcItem.getId(),
			item.getId(),
			item.getRId(),
			item.getItemName(),
			item.getItemType().name(),
			item.getItemGrade().name(),
			item.getDescription(),
			item.getPrice(),
			item.getSellPrice(),
			npcItem.getQuantity(),
			npcItem.getSortOrder()
		);
	}
}
