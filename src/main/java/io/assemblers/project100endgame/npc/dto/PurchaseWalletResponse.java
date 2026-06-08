package io.assemblers.project100endgame.npc.dto;

import io.assemblers.project100endgame.wallet.entity.Wallet;

public record PurchaseWalletResponse(
	Long gold,
	Long gem
) {
	public static PurchaseWalletResponse from(Wallet wallet) {
		return new PurchaseWalletResponse(
			wallet.getGold(),
			wallet.getGem()
		);
	}
}
