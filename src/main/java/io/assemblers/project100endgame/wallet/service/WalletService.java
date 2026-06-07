package io.assemblers.project100endgame.wallet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.wallet.dto.WalletResponse;
import io.assemblers.project100endgame.wallet.entity.Wallet;
import io.assemblers.project100endgame.wallet.exception.WalletNotFoundException;
import io.assemblers.project100endgame.wallet.repository.WallteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WalletService {

	private final WallteRepository walletRepository;

	public WalletResponse getWallet(Long userId) {
		Wallet wallet = walletRepository.findByUserId(userId)
			.orElseThrow(WalletNotFoundException::new);

		return WalletResponse.from(wallet);
	}

}
