package io.assemblers.project100endgame.npc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.assemblers.project100endgame.npc.entity.Npc;

public interface NpcRepository extends JpaRepository<Npc, Long> {

	@Query("""
		select distinct n
		from Npc n
		left join fetch n.npcItem ni
		left join fetch ni.item
		where n.active = true
		order by n.id asc, ni.sortOrder asc
		""")
	List<Npc> findAllActiveWithItems();

	@Query("""
		select distinct n
		from Npc n
		left join fetch n.npcItems ni
		left join fetch ni.item
		where n.id = :npcId
		and n.active = true
		""")
	Optional<Npc> findActiveByIdWithItems(Long npcId);
}
