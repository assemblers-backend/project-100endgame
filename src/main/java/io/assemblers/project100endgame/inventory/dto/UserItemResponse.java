package io.assemblers.project100endgame.inventory.dto;

import java.time.LocalDateTime;

import io.assemblers.project100endgame.inventory.entity.UserItem;
import io.assemblers.project100endgame.item.entity.Item;

public record UserItemResponse(
	Long userItemId,
	Long itemId,
	String rId,
	String itemName,
	String itemType,
	String itemGrade,
	String description,
	Long price,
	Long sellPrice,
	Long quantity,
	boolean equipped,
	LocalDateTime acquiredAt
) {
	public static UserItemResponse from(UserItem userItem) {
		Item item = userItem.getItem();

		return new UserItemResponse(
			userItem.getId(),
			item.getId(),
			item.getRId(),
			item.getItemName(),
			item.getItemType().name(),
			item.getItemGrade().name(),
			item.getDescription(),
			item.getPrice(),
			item.getSellPrice(),
			userItem.getQuantity(),
			userItem.isEquipped(),
			userItem.getAcquiredAt()
		);
	}
}
