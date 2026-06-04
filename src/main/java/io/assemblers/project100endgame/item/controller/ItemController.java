package io.assemblers.project100endgame.item.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.assemblers.project100endgame.item.dto.ItemResponse;
import io.assemblers.project100endgame.item.service.ItemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

	private final ItemService itemService;

	@GetMapping
	public List<ItemResponse> getItems() {
		return itemService.getItems();
	}

	@GetMapping("/{id}")
	public ItemResponse getItem(@PathVariable Long id) {
		return itemService.getItem(id);
	}
}
