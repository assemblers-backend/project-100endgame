package io.assemblers.project100endgame.npc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.assemblers.project100endgame.npc.entity.NpcItem;

public interface NpcItemRepository extends JpaRepository<NpcItem, Long> {
	@Query("""
		select ni
		from NpcItem ni
		join fetch ni.npc
		join fetch ni.item
		where ni.id = :npcItemId
		and ni.npc.id = :npcId
		""")
	Optional<NpcItem> findByIdAndNpcIdWithItem(
		@Param("npcId") Long npcId,
		@Param("npcItemId") Long npcItemId
	);
}
