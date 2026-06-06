package io.assemblers.project100endgame.npc.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.assemblers.project100endgame.common.exception.GlobalExceptionHandler;
import io.assemblers.project100endgame.npc.dto.NpcResponse;
import io.assemblers.project100endgame.npc.dto.NpcShopItemResponse;
import io.assemblers.project100endgame.npc.exception.NpcNotFoundException;
import io.assemblers.project100endgame.npc.service.NpcService;

@WebMvcTest(NpcController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
public class NpcControllerTest {
	/*
	GET /api/v1/npcs
	→ 200 OK
	→ success true
	→ msg "NPC 목록을 조회했습니다."
	→ data 배열

	GET /api/v1/npcs/{id}
	→ 200 OK
	→ success true
	→ msg "NPC를 조회했습니다."
	→ data 객체

	GET /api/v1/npcs/9999
	→ 404 Not Found
	→ success false
	→ msg "NPC를 찾을 수 없습니다."
	→ data null
	 */

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private NpcService npcService;

	@MockitoBean
	private JpaMetamodelMappingContext jpaMetamodelMappingContext;

	@Test
	@DisplayName("NPC 목록을 조회한다.")
	void getNpcs() throws Exception {
		//given
		NpcShopItemResponse shopItemResponse = new NpcShopItemResponse(
			1L,
			1L,
			"sword_001",
			"연습용 검",
			"WEAPON",
			"COMMON",
			"초보자용 기본 검입니다.",
			100L,
			50L,
			99L,
			1L
		);

		NpcResponse npcResponse = new NpcResponse(
			1L,
			"npc_merchant_001",
			"상인 밥",
			"마을 입구의 상인입니다.",
			"village_entrance",
			true,
			List.of(shopItemResponse)
		);

		when(npcService.getNpcs())
			.thenReturn(List.of(npcResponse));

		// when & then
		mockMvc.perform(get("/api/v1/npcs"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.msg").value("NPC 목록을 조회했습니다."))
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data[0].npcId").value(1))
			.andExpect(jsonPath("$.data[0].rId").value("npc_merchant_001"))
			.andExpect(jsonPath("$.data[0].name").value("상인 밥"))
			.andExpect(jsonPath("$.data[0].description").value("마을 입구의 상인입니다."))
			.andExpect(jsonPath("$.data[0].locationKey").value("village_entrance"))
			.andExpect(jsonPath("$.data[0].active").value(true))
			.andExpect(jsonPath("$.data[0].shopItems").isArray())
			.andExpect(jsonPath("$.data[0].shopItems[0].npcItemId").value(1))
			.andExpect(jsonPath("$.data[0].shopItems[0].itemId").value(1))
			.andExpect(jsonPath("$.data[0].shopItems[0].rId").value("sword_001"))
			.andExpect(jsonPath("$.data[0].shopItems[0].itemName").value("연습용 검"))
			.andExpect(jsonPath("$.data[0].shopItems[0].itemType").value("WEAPON"))
			.andExpect(jsonPath("$.data[0].shopItems[0].itemGrade").value("COMMON"))
			.andExpect(jsonPath("$.data[0].shopItems[0].price").value(100))
			.andExpect(jsonPath("$.data[0].shopItems[0].sellPrice").value(50))
			.andExpect(jsonPath("$.data[0].shopItems[0].quantity").value(99))
			.andExpect(jsonPath("$.data[0].shopItems[0].sortOrder").value(1));
	}

	@Test
	@DisplayName("NPC를 단건 조회한다")
	void getNpc() throws Exception {
		// given
		Long npcId = 1L;

		NpcShopItemResponse shopItemResponse = new NpcShopItemResponse(
			1L,
			6L,
			"potion_001",
			"체력 회복 물약",
			"CONSUMABLE",
			"COMMON",
			"체력을 소량 회복하는 기본 물약입니다.",
			50L,
			20L,
			50L,
			1L
		);

		NpcResponse npcResponse = new NpcResponse(
			1L,
			"npc_healer_001",
			"치유사 엘라",
			"물약을 판매하는 치유사입니다.",
			"healer_house",
			true,
			List.of(shopItemResponse)
		);

		when(npcService.getNpc(npcId))
			.thenReturn(npcResponse);

		// when & then
		mockMvc.perform(get("/api/v1/npcs/{id}", npcId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.msg").value("NPC를 조회했습니다."))
			.andExpect(jsonPath("$.data.npcId").value(1))
			.andExpect(jsonPath("$.data.rId").value("npc_healer_001"))
			.andExpect(jsonPath("$.data.name").value("치유사 엘라"))
			.andExpect(jsonPath("$.data.description").value("물약을 판매하는 치유사입니다."))
			.andExpect(jsonPath("$.data.locationKey").value("healer_house"))
			.andExpect(jsonPath("$.data.active").value(true))
			.andExpect(jsonPath("$.data.shopItems").isArray())
			.andExpect(jsonPath("$.data.shopItems[0].itemName").value("체력 회복 물약"))
			.andExpect(jsonPath("$.data.shopItems[0].itemType").value("CONSUMABLE"))
			.andExpect(jsonPath("$.data.shopItems[0].quantity").value(50));
	}

	@Test
	@DisplayName("존재하지 않는 NPC를 조회하면 404를 반환한다")
	void getNpc_notFound() throws Exception {
		// given
		Long npcId = 9999L;

		when(npcService.getNpc(npcId))
			.thenThrow(new NpcNotFoundException());

		// when & then
		mockMvc.perform(get("/api/v1/npcs/{id}", npcId))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.msg").value("NPC를 찾을 수 없습니다."))
			.andExpect(jsonPath("$.data").doesNotExist());
	}
}
