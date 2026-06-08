package io.assemblers.project100endgame.item.dto;

import io.assemblers.project100endgame.item.entity.Item;

public record ItemResponse(
	Long itemId,
	String rId,
	String itemName,
	String itemType,
	String itemGrade,
	String description,
	Long price,
	Long sellPrice
) {
	public static ItemResponse from(Item item) {
		return new ItemResponse(
			item.getId(),
			item.getRId(),
			item.getItemName(),
			item.getItemType().name(),
			item.getItemGrade().name(),
			item.getDescription(),
			item.getPrice(),
			item.getSellPrice()
		);
	}
}
