package io.assemblers.project100endgame.useritem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.item.entity.Item;
import io.assemblers.project100endgame.item.exception.ItemNotFoundException;
import io.assemblers.project100endgame.item.repository.ItemRepository;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.useritem.dto.ItemPickupRequest;
import io.assemblers.project100endgame.useritem.dto.UserItemResponse;
import io.assemblers.project100endgame.useritem.entity.UserItem;
import io.assemblers.project100endgame.useritem.exception.InvalidItemQuantityException;
import io.assemblers.project100endgame.useritem.exception.UserItemNotFoundException;
import io.assemblers.project100endgame.useritem.repository.UserItemRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserItemService {

	private final UserItemRepository userItemRepository;
	private final ItemRepository itemRepository;
	private final EntityManager entityManager;

	public List<UserItemResponse> getInventory(Long userId) {
		return userItemRepository.findAllByUserIdWithItem(userId)
			.stream()
			.map(UserItemResponse::from)
			.toList();
	}

	@Transactional
	public UserItemResponse pickupItem(Long userId, ItemPickupRequest request) {
		validatePickupRequest(request);

		Item item = itemRepository.findById(request.itemId())
			.orElseThrow(ItemNotFoundException::new);

		UserItem userItem = userItemRepository
			.findByUserIdAndItemIdWithItem(userId, request.itemId())
			.map(existingUserItem -> {
				existingUserItem.increaseQuantity(request.quantity());
				return existingUserItem;
			})
			.orElseGet(() -> {
				Users user = entityManager.getReference(Users.class, userId);

				UserItem newUserItem = UserItem.builder()
					.user(user)
					.item(item)
					.quantity(request.quantity())
					.build();

				return userItemRepository.save(newUserItem);
			});

		return UserItemResponse.from(userItem);

	}

	private void validatePickupRequest(ItemPickupRequest request) {
		if (request == null || request.itemId() == null) {
			throw new ItemNotFoundException();
		}

		if (request.quantity() == null || request.quantity() <= 0) {
			throw new InvalidItemQuantityException();
		}
	}

	@Transactional
	public void discardItem(Long userId, Long itemId, Long quantity) {
		validateDiscardQuantity(quantity);

		UserItem userItem = userItemRepository
			.findByUserIdAndItemIdWithItem(userId, itemId)
			.orElseThrow(UserItemNotFoundException::new);

		userItem.decreaseQuantity(quantity);

		if (userItem.isEmpty()) {
			userItemRepository.delete(userItem);
		}
	}

	private void validateDiscardQuantity(Long quantity) {
		if (quantity == null || quantity <= 0) {
			throw new InvalidItemQuantityException();
		}
	}
}
