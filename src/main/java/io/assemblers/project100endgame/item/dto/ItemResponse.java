package io.assemblers.project100endgame.item.dto;

import io.assemblers.project100endgame.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponse {

	private Long itemId;
	private String rId;
	private String itemName;
	private String itemType;
	private String itemGrade;
	private String description;
	private Long price;
	private Long sellPrice;

	public static ItemResponse from(Item item) {
		return ItemResponse.builder()
			.itemId(item.getId())
			.rId(item.getRId())
			.itemName(item.getItemName())
			.itemType(item.getItemType().name())
			.itemGrade(item.getItemGrade().name())
			.description(item.getDescription())
			.price(item.getPrice())
			.sellPrice(item.getSellPrice())
			.build();
	}
}
