package io.assemblers.project100endgame.friend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.friend.dto.FriendResponse;
import io.assemblers.project100endgame.friend.entity.FriendStatus;
import io.assemblers.project100endgame.friend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

	private final FriendRepository friendRepository;

	public List<FriendResponse> getFriends(Long userId) {
		return friendRepository.findFriendsByUserIdAndStatus(userId, FriendStatus.ACCEPTED)
			.stream()
			.map(friend -> FriendResponse.friendOf(friend, userId))
			.toList();
	}
}
