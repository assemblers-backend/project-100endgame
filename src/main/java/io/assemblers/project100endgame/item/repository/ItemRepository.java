package io.assemblers.project100endgame.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.assemblers.project100endgame.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	//findAll
	//findById(id)
}
