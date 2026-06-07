package io.assemblers.project100endgame.useritem.dto;

public record ItemPickupRequest(
	Long itemId,
	Long quantity
) {
}