package io.assemblers.project100endgame.npc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.assemblers.project100endgame.npc.entity.NpcItem;

public interface NpcItemRepository extends JpaRepository<NpcItem, Long> {

}
