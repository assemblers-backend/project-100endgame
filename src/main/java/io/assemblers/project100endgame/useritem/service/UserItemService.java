package io.assemblers.project100endgame.useritem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.useritem.dto.UserItemResponse;
import io.assemblers.project100endgame.useritem.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserItemService {

	private final UserItemRepository userItemRepository;

	public List<UserItemResponse> getInventory(Long userId) {
		return userItemRepository.findAllByUserIdWithItem(userId)
			.stream()
			.map(UserItemResponse::from)
			.toList();
	}
}
