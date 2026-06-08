package io.assemblers.project100endgame.friend.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

	@Query("""
        select count(f)
        from Friend f
        where (
            (f.fromUser.id = :fromUserId and f.toUser.id = :toUserId)
            or
            (f.fromUser.id = :toUserId and f.toUser.id = :fromUserId)
        )
        and f.friendStatus in :statuses
        """)
	long countExistingRelationOrRequest(
		@Param("fromUserId") Long fromUserId,
		@Param("toUserId") Long toUserId,
		@Param("statuses") Collection<FriendStatus> statuses
	);

	@Query("""
    select f
    from Friend f
    join fetch f.fromUser
    join fetch f.toUser
    where f.id = :requestId
    and f.friendStatus = :friendStatus
    """)
	Optional<Friend> findByIdAndFriendStatusWithUsers(
		@Param("requestId") Long requestId,
		@Param("friendStatus") FriendStatus friendStatus
	);

	@Query("""
    select f
    from Friend f
    join fetch f.fromUser
    join fetch f.toUser
    where f.friendStatus = :friendStatus
    and (
        (f.fromUser.id = :userId and f.toUser.id = :friendUserId)
        or
        (f.fromUser.id = :friendUserId and f.toUser.id = :userId)
    )
    """)
	Optional<Friend> findFriendRelation(
		@Param("userId") Long userId,
		@Param("friendUserId") Long friendUserId,
		@Param("friendStatus") FriendStatus friendStatus
	);
}
