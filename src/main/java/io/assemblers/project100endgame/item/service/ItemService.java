package io.assemblers.project100endgame.item.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.assemblers.project100endgame.item.dto.ItemResponse;
import io.assemblers.project100endgame.item.entity.Item;
import io.assemblers.project100endgame.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

	private final ItemRepository itemRepository;

	public List<ItemResponse> getItems(){
		return itemRepository.findAll()
			.stream()
			.map(ItemResponse::from)
			.toList();
	}

	public ItemResponse getItem(Long itemId){
		Item item = itemRepository.findById(itemId)
			.orElseThrow(()->new IllegalArgumentException("아이템을 찾을 수 없습니다."));

		return ItemResponse.from(item);
	}
}
