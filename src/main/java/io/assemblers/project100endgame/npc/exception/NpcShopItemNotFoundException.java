package io.assemblers.project100endgame.npc.exception;

public class NpcShopItemNotFoundException extends RuntimeException {
	public NpcShopItemNotFoundException() {
		super("상점 아이템을 찾을 수 없습니다.");
	}
}
