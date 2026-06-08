package io.assemblers.project100endgame.friend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.friend.dto.FriendRequestCreateRequest;
import io.assemblers.project100endgame.friend.dto.FriendResponse;
import io.assemblers.project100endgame.friend.entity.Friend;
import io.assemblers.project100endgame.friend.entity.FriendStatus;
import io.assemblers.project100endgame.friend.exception.DuplicateFriendRequestException;
import io.assemblers.project100endgame.friend.exception.FriendRequestAcceptNotAllowedException;
import io.assemblers.project100endgame.friend.exception.FriendRequestNotFoundException;
import io.assemblers.project100endgame.friend.exception.FriendTargetNotFoundException;
import io.assemblers.project100endgame.friend.exception.SelfFriendRequestException;
import io.assemblers.project100endgame.friend.repository.FriendRepository;
import io.assemblers.project100endgame.user.entity.Users;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

	private final FriendRepository friendRepository;
	private final UsersRepository usersRepository;

	public List<FriendResponse> getFriends(Long userId) {
		return friendRepository.findFriendsByUserIdAndStatus(userId, FriendStatus.ACCEPTED)
			.stream()
			.map(friend -> FriendResponse.friendOf(friend, userId))
			.toList();
	}

	public List<FriendResponse> getReceivedFriendRequests(Long userId) {
		return friendRepository.findReceivedRequestsByUserIdAndStatus(userId, FriendStatus.PENDING)
			.stream()
			.map(FriendResponse::requestFrom)
			.toList();
	}

	@Transactional
	public FriendResponse sendFriendRequest(Long fromUserId, FriendRequestCreateRequest request) {
		validateRequest(request);

		Long toUserId = request.toUserId();

		if (fromUserId.equals(toUserId)) {
			throw new SelfFriendRequestException();
		}

		Users fromUser = usersRepository.findById(fromUserId)
			.orElseThrow(FriendTargetNotFoundException::new);

		Users toUser = usersRepository.findById(toUserId)
			.orElseThrow(FriendTargetNotFoundException::new);

		long existingCount = friendRepository.countExistingRelationOrRequest(
			fromUserId,
			toUserId,
			List.of(FriendStatus.PENDING, FriendStatus.ACCEPTED)
		);

		if (existingCount > 0) {
			throw new DuplicateFriendRequestException();
		}

		Friend friend = Friend.builder()
			.fromUser(fromUser)
			.toUser(toUser)
			.build();

		Friend savedFriend = friendRepository.save(friend);

		return FriendResponse.requestFrom(savedFriend);
	}

	private void validateRequest(FriendRequestCreateRequest request) {
		if (request == null || request.toUserId() == null) {
			throw new FriendTargetNotFoundException();
		}
	}

	@Transactional
	public FriendResponse acceptFriendRequest(Long userId, Long requestId) {
		Friend friend = friendRepository
			.findByIdAndFriendStatusWithUsers(requestId, FriendStatus.PENDING)
			.orElseThrow(FriendRequestNotFoundException::new);

		if (!friend.getToUser().getId().equals(userId)) {
			throw new FriendRequestAcceptNotAllowedException();
		}

		friend.accept();

		return FriendResponse.requestFrom(friend);
	}
}
