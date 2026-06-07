package io.assemblers.project100endgame.wallet.dto;

import io.assemblers.project100endgame.wallet.entity.Wallet;

public record WalletResponse(
	Long walletId,
	Long gold,
	Long gem
) {

	public static WalletResponse from(Wallet wallet) {
		return new WalletResponse(
			wallet.getId(),
			wallet.getGold(),
			wallet.getGem()
		);
	}
}
