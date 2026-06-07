package io.assemblers.project100endgame.useritem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.assemblers.project100endgame.useritem.entity.UserItem;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
	/*
	1. 특정 유저의 인벤토리 전체 조회
	2. 특정 유저가 특정 아이템을 가지고 있는지 조회
	 */

	@Query("""
		select ui
		from UserItem ui
		join fetch ui.item i
		where ui.user.id = :userId
		order by ui.acquiredAt desc
		""")
	List<UserItem> findAllByUserIdWithItem(@Param("userId") Long userId);

	// UserId && ItemId -> 해당 유저가 특정 아이템을 가지고 있는지.
	@Query("""
		select ui
		from UserItem ui
		join fetch ui.item i
		where ui.user.id = :userId
		and ui.item.id = :itemId
		""")
	Optional<UserItem> findByUserIdAndItemIdWithItem(
		@Param("userId") Long userId,
		@Param("itemId") Long itemId
	);

}
