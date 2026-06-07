package io.assemblers.project100endgame.npc.exception;

public class NpcNotFoundException extends RuntimeException {

	public NpcNotFoundException() {
		super("NPC를 찾을 수 없습니다.");
	}
}
