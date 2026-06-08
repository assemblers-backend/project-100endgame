package io.assemblers.project100endgame.friend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.assemblers.project100endgame.friend.entity.Friend;
import io.assemblers.project100endgame.friend.entity.FriendStatus;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	@Query("""
		select f
		from Friend f
		join fetch f.fromUser
		join fetch f.toUser
		where f.friendStatus = :status
		and (
		    f.fromUser.id = :userId
		    or f.toUser.id = :userId
		)
		order by f.createdAt desc
		""")
	List<Friend> findFriendsByUserIdAndStatus(
		@Param("userId") Long userId,
		@Param("status") FriendStatus status
	);

	@Query("""
		select f
		from Friend f
		join fetch f.fromUser
		join fetch f.toUser
		where f.toUser.id = :userId
		and f.friendStatus = :status
		order by f.createdAt desc
		""")
	List<Friend> findReceivedRequestsByUserIdAndStatus(
		@Param("userId") Long userId,
		@Param("status") FriendStatus status
	);
}
