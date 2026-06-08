package io.assemblers.project100endgame.npc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.npc.dto.NpcItemPurchaseRequest;
import io.assemblers.project100endgame.npc.dto.NpcItemPurchaseResponse;
import io.assemblers.project100endgame.npc.entity.NpcItem;
import io.assemblers.project100endgame.npc.exception.InvalidPurchaseQuantityException;
import io.assemblers.project100endgame.npc.exception.NpcNotFoundException;
import io.assemblers.project100endgame.npc.exception.NpcShopItemNotFoundException;
import io.assemblers.project100endgame.npc.repository.NpcItemRepository;
import io.assemblers.project100endgame.npc.repository.NpcRepository;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.useritem.dto.UserItemResponse;
import io.assemblers.project100endgame.useritem.entity.UserItem;
import io.assemblers.project100endgame.useritem.repository.UserItemRepository;
import io.assemblers.project100endgame.wallet.entity.Wallet;
import io.assemblers.project100endgame.wallet.exception.WalletNotFoundException;
import io.assemblers.project100endgame.wallet.repository.WalletRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NpcPurchaseService {

	private final NpcRepository npcRepository;
	private final NpcItemRepository npcItemRepository;
	private final WalletRepository walletRepository;
	private final UserItemRepository userItemRepository;
	private final EntityManager entityManager;

	@Transactional
	public NpcItemPurchaseResponse purchaseItem(
		Long userId,
		Long npcId,
		Long npcItemId,
		NpcItemPurchaseRequest request
	) {
		validateRequest(request);

		Long purchaseQuantity = request.quantity().longValue();

		if (!npcRepository.existsByIdAndActiveTrue(npcId)) {
			throw new NpcNotFoundException();
		}

		NpcItem npcItem = npcItemRepository.findByIdAndNpcIdWithItem(npcId, npcItemId)
			.orElseThrow(NpcShopItemNotFoundException::new);

		Wallet wallet = walletRepository.findByUserId(userId)
			.orElseThrow(WalletNotFoundException::new);

		Long totalPrice = npcItem.getItem().getPrice() * purchaseQuantity;

		wallet.spendGold(totalPrice);

		// 상점 재고를 차감합니다.
		npcItem.decreaseQuantity(purchaseQuantity);

		UserItem userItem = addItemToInventory(userId, npcItem, purchaseQuantity);

		return NpcItemPurchaseResponse.of(
			wallet,
			UserItemResponse.from(userItem)
		);
	}

	private UserItem addItemToInventory(Long userId, NpcItem npcItem, Long quantity) {
		return userItemRepository
			.findByUserIdAndItemIdWithItem(userId, npcItem.getItem().getId())
			.map(existingUserItem -> {
				existingUserItem.increaseQuantity(quantity);
				return existingUserItem;
			})
			.orElseGet(() -> {
				Users user = entityManager.getReference(Users.class, userId);

				UserItem newUserItem = UserItem.builder()
					.user(user)
					.item(npcItem.getItem())
					.quantity(quantity)
					.build();

				return userItemRepository.save(newUserItem);
			});
	}

	private void validateRequest(NpcItemPurchaseRequest request) {
		if (request == null || request.quantity() == null || request.quantity() <= 0) {
			throw new InvalidPurchaseQuantityException();
		}
	}
}
