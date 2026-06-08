package io.assemblers.project100endgame.user.dto;

import java.util.List;

import io.assemblers.project100endgame.useritem.dto.UserItemResponse;

public record UserEntireInfoResponse(
	UserResponse userResponse,
	ProfileDto profile,
	WalletDto wallet,
	List<UserItemResponse> inventory,
	Long friendCount
) {
}
