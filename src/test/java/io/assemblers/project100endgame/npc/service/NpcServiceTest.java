package io.assemblers.project100endgame.npc.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.assemblers.project100endgame.item.constant.ItemGrade;
import io.assemblers.project100endgame.item.constant.ItemType;
import io.assemblers.project100endgame.item.entity.Item;
import io.assemblers.project100endgame.npc.dto.NpcResponse;
import io.assemblers.project100endgame.npc.entity.Npc;
import io.assemblers.project100endgame.npc.entity.NpcItem;
import io.assemblers.project100endgame.npc.exception.NpcNotFoundException;
import io.assemblers.project100endgame.npc.repository.NpcRepository;

@ExtendWith(MockitoExtension.class)
public class NpcServiceTest {
	/*
	getNpcs()
	→ NPC 목록을 NpcResponse 목록으로 변환하는지

	getNpc()
	→ NPC 단건을 NpcResponse로 변환하는지

	getNpc()
	→ 없는 NPC면 NpcNotFoundException을 던지는지
	 */

	@Mock
	private NpcRepository npcRepository;

	@InjectMocks
	private NpcService npcService;

	@Test
	@DisplayName("NPC 목록을 조회하면 NpcResponse 목록으로 변환해서 반환한다.")
	void getNpcs() {
		// given
		Item item = Item.builder()
			.rId("sword_001")
			.itemName("연습용 검")
			.itemType(ItemType.WEAPON)
			.itemGrade(ItemGrade.COMMON)
			.description("초보자용 기본 검입니다.")
			.price(100L)
			.sellPrice(50L)
			.build();

		Npc npc = Npc.builder()
			.rId("npc_merchant_001")
			.name("상인 밥")
			.description("마을 입구의 상인입니다.")
			.locationKey("village_entrance")
			.build();

		NpcItem npcItem = NpcItem.builder()
			.npc(npc)
			.item(item)
			.quantity(99L)
			.sortOrder(1L)
			.build();

		npc.getNpcItems().add(npcItem);

		when(npcRepository.findAllActiveWithItems())
			.thenReturn(List.of(npc));

		// when
		List<NpcResponse> result = npcService.getNpcs();

		// then
		assertThat(result).hasSize(1);

		NpcResponse response = result.get(0);
		assertThat(response.rId()).isEqualTo("npc_merchant_001");
		assertThat(response.name()).isEqualTo("상인 밥");
		assertThat(response.description()).isEqualTo("마을 입구의 상인입니다.");
		assertThat(response.locationKey()).isEqualTo("village_entrance");
		assertThat(response.active()).isTrue();

		assertThat(response.shopItems()).hasSize(1);
		assertThat(response.shopItems().get(0).rId()).isEqualTo("sword_001");
		assertThat(response.shopItems().get(0).itemName()).isEqualTo("연습용 검");
		assertThat(response.shopItems().get(0).itemType()).isEqualTo("WEAPON");
		assertThat(response.shopItems().get(0).itemGrade()).isEqualTo("COMMON");
		assertThat(response.shopItems().get(0).quantity()).isEqualTo(99L);
		assertThat(response.shopItems().get(0).sortOrder()).isEqualTo(1L);
	}

	@Test
	@DisplayName("NPC 단건을 조회하면 NpcResponse로 변환해서 반환한다")
	void getNpc() {
		// given
		Long npcId = 1L;

		Item item = Item.builder()
			.rId("potion_001")
			.itemName("체력 회복 물약")
			.itemType(ItemType.CONSUMABLE)
			.itemGrade(ItemGrade.COMMON)
			.description("체력을 회복하는 물약입니다.")
			.price(50L)
			.sellPrice(20L)
			.build();

		Npc npc = Npc.builder()
			.rId("npc_healer_001")
			.name("치유사 엘라")
			.description("물약을 판매하는 치유사입니다.")
			.locationKey("healer_house")
			.build();

		NpcItem npcItem = NpcItem.builder()
			.npc(npc)
			.item(item)
			.quantity(50L)
			.sortOrder(1L)
			.build();

		npc.getNpcItems().add(npcItem);

		when(npcRepository.findActiveByIdWithItems(npcId))
			.thenReturn(Optional.of(npc));

		// when
		NpcResponse response = npcService.getNpc(npcId);

		// then
		assertThat(response.rId()).isEqualTo("npc_healer_001");
		assertThat(response.name()).isEqualTo("치유사 엘라");
		assertThat(response.shopItems()).hasSize(1);
		assertThat(response.shopItems().get(0).itemName()).isEqualTo("체력 회복 물약");
	}

	@Test
	@DisplayName("존재하지 않는 NPC를 단건 조회하면 NpcNotFoundException이 발생한다")
	void getNpc_notFound() {
		// given
		Long npcId = 9999L;

		when(npcRepository.findActiveByIdWithItems(npcId))
			.thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> npcService.getNpc(npcId))
			.isInstanceOf(NpcNotFoundException.class)
			.hasMessage("NPC를 찾을 수 없습니다.");
	}
}
