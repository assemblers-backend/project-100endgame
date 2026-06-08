package io.assemblers.project100endgame.wallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.common.response.GeneralResponse;
import io.assemblers.project100endgame.wallet.dto.WalletResponse;
import io.assemblers.project100endgame.wallet.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/wallet")
public class WalletController {

	private final WalletService walletService;
	private final TokenService tokenService;

	@GetMapping
	public ResponseEntity<GeneralResponse<WalletResponse>> getWallet(HttpServletRequest requestServlet) {
		Long userId = tokenService.authValidate(requestServlet);

		return ResponseEntity.ok(
			GeneralResponse.success("지갑을 조회했습니다.", walletService.getWallet(userId))
		);
	}
}
