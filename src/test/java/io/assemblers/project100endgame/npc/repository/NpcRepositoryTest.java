package io.assemblers.project100endgame.npc.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import io.assemblers.project100endgame.item.constant.ItemGrade;
import io.assemblers.project100endgame.item.constant.ItemType;
import io.assemblers.project100endgame.item.entity.Item;
import io.assemblers.project100endgame.item.repository.ItemRepository;
import io.assemblers.project100endgame.npc.entity.Npc;
import io.assemblers.project100endgame.npc.entity.NpcItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// 기존 data.sql 테스트와 충돌이 일어나지 않도록, properties 설정.
@DataJpaTest(properties = {
	"spring.sql.init.mode=never"
})
public class NpcRepositoryTest {

	@Autowired
	private NpcRepository npcRepository;

	@Autowired
	private NpcItemRepository npcItemRepository;

	@Autowired
	private ItemRepository itemRepository;

	@PersistenceContext
	private EntityManager em;

	@Test
	@DisplayName("활성화된 NPC 목록을 상점 아이템과 함께 조회한다")
	void findAllActiveWithItems() {
		// given
		Item item = Item.builder()
			.rId("sword_001")
			.itemName("연습용 검")
			.itemType(ItemType.WEAPON)
			.itemGrade(ItemGrade.COMMON)
			.description("초보자용 검입니다.")
			.price(100L)
			.sellPrice(50L)
			.build();

		itemRepository.save(item);

		Npc npc = Npc.builder()
			.rId("npc_merchant_001")
			.name("상인 밥")
			.description("마을 입구의 상인입니다.")
			.locationKey("village_entrance")
			.build();

		npcRepository.save(npc);

		NpcItem npcItem = NpcItem.builder()
			.npc(npc)
			.item(item)
			.quantity(99L)
			.sortOrder(1L)
			.build();

		npcItemRepository.save(npcItem);

		em.flush();
		em.clear();

		// when
		List<Npc> result = npcRepository.findAllActiveWithItems();

		// then
		assertThat(result).hasSize(1);

		Npc findNpc = result.get(0);
		assertThat(findNpc.getName()).isEqualTo("상인 밥");
		assertThat(findNpc.getNpcItems()).hasSize(1);

		NpcItem findNpcItem = findNpc.getNpcItems().get(0);
		assertThat(findNpcItem.getQuantity()).isEqualTo(99L);
		assertThat(findNpcItem.getItem().getItemName()).isEqualTo("연습용 검");

	}

	@Test
	@DisplayName("비활성화된 NPC는 목록 조회에서 제외된다")
	void findAllActiveWithItems_excludeInactiveNpc() {
		// given
		Item potion = Item.builder()
			.rId("potion_001")
			.itemName("체력 회복 물약")
			.itemType(ItemType.CONSUMABLE)
			.itemGrade(ItemGrade.COMMON)
			.description("체력을 회복하는 물약입니다.")
			.price(50L)
			.sellPrice(20L)
			.build();

		itemRepository.save(potion);

		Npc activeNpc = Npc.builder()
			.rId("npc_active_001")
			.name("활성 NPC")
			.description("조회되어야 하는 NPC입니다.")
			.locationKey("active_area")
			.build();

		Npc inactiveNpc = Npc.builder()
			.rId("npc_inactive_001")
			.name("비활성 NPC")
			.description("조회되면 안 되는 NPC입니다.")
			.locationKey("inactive_area")
			.build();

		inactiveNpc.deactivate();

		npcRepository.save(activeNpc);
		npcRepository.save(inactiveNpc);

		npcItemRepository.save(NpcItem.builder()
			.npc(activeNpc)
			.item(potion)
			.quantity(10L)
			.sortOrder(1L)
			.build());

		npcItemRepository.save(NpcItem.builder()
			.npc(inactiveNpc)
			.item(potion)
			.quantity(10L)
			.sortOrder(1L)
			.build());

		em.flush();
		em.clear();

		// when
		List<Npc> result = npcRepository.findAllActiveWithItems();

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getName()).isEqualTo("활성 NPC");
	}

	@Test
	@DisplayName("활성화된 NPC를 ID로 상점 아이템과 함께 단건 조회한다")
	void findActiveByIdWithItems() {
		// given
		Item item = Item.builder()
			.rId("potion_001")
			.itemName("체력 회복 물약")
			.itemType(ItemType.CONSUMABLE)
			.itemGrade(ItemGrade.COMMON)
			.description("체력을 회복합니다.")
			.price(50L)
			.sellPrice(20L)
			.build();

		itemRepository.save(item);

		Npc npc = Npc.builder()
			.rId("npc_healer_001")
			.name("치유사 엘라")
			.description("물약을 판매하는 치유사입니다.")
			.locationKey("healer_house")
			.build();

		npcRepository.save(npc);

		NpcItem npcItem = NpcItem.builder()
			.npc(npc)
			.item(item)
			.quantity(50L)
			.sortOrder(1L)
			.build();

		npcItemRepository.save(npcItem);

		em.flush();
		em.clear();

		// when
		Optional<Npc> result = npcRepository.findActiveByIdWithItems(npc.getId());

		// then
		assertThat(result).isPresent();

		Npc findNpc = result.get();
		assertThat(findNpc.getName()).isEqualTo("치유사 엘라");
		assertThat(findNpc.getNpcItems()).hasSize(1);
		assertThat(findNpc.getNpcItems().get(0).getItem().getItemName()).isEqualTo("체력 회복 물약");
	}

	@Test
	@DisplayName("존재하지 않는 NPC ID로 조회하면 Optional.empty를 반환한다")
	void findActiveByIdWithItems_notFound() {
		// when
		Optional<Npc> result = npcRepository.findActiveByIdWithItems(9999L);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("비활성화된 NPC는 ID로 조회해도 Optional.empty를 반환한다")
	void findActiveByIdWithItems_inactiveNPC() {
		// given
		Npc npc = Npc.builder()
			.rId("npc_hidden_001")
			.name("숨겨진 NPC")
			.description("비활성화된 NPC입니다.")
			.locationKey("hidden_area")
			.build();

		npc.deactivate();

		npcRepository.save(npc);

		em.flush();
		em.clear();

		// when
		Optional<Npc> result = npcRepository.findActiveByIdWithItems(npc.getId());

		// then
		assertThat(result).isEmpty();
	}
}
