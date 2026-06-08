package io.assemblers.project100endgame.npc.dto;

import io.assemblers.project100endgame.useritem.dto.UserItemResponse;
import io.assemblers.project100endgame.wallet.entity.Wallet;

public record NpcItemPurchaseResponse(
	PurchaseWalletResponse wallet,
	UserItemResponse acquiredItem
) {

	public static NpcItemPurchaseResponse of(Wallet wallet, UserItemResponse acquiredItem) {
		return new NpcItemPurchaseResponse(
			PurchaseWalletResponse.from(wallet),
			acquiredItem
		);
	}
}
