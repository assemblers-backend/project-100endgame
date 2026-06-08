package io.assemblers.project100endgame.wallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.wallet.dto.WalletResponse;
import io.assemblers.project100endgame.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/wallet")
public class WalletController {

	private final WalletService walletService;

	@GetMapping
	public ResponseEntity<GeneralResponse<WalletResponse>> getWallet() {
		// TODO: 인증 구현 후 로그인 유저 ID로 교체
		Long userId = 1L;

		return ResponseEntity.ok(
			GeneralResponse.success("지갑을 조회했습니다.", walletService.getWallet(userId))
		);
	}
}
