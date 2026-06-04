package io.assemblers.project100endgame.npc.entity;

import io.assemblers.project100endgame.common.entity.BaseEntity;
import io.assemblers.project100endgame.item.entity.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="npc_shop_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NpcItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long sortOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "npc_id", nullable = false)
    private Npc npc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Builder
    public NpcItem(
            Long quantity,
            Long sortOrder,
            Npc npc,
            Item item
    ){
        //검증
        validateNum(quantity, "물품은 0개 이상이어야 합니다.");
        validateNum(sortOrder, "정렬 순서는 0 이상이어야 합니다.");
        validateNpc(npc);
        validateItem(item);

        this.quantity = quantity;
        this.sortOrder = sortOrder;
        this.npc = npc;
        this.item = item;

    }

    public void updateQuantity(Long quantity){
        validateNum(quantity, "물품은 0개 이상이어야 합니다.");

        this.quantity = quantity;
    }

    public void updateSortOrder(Long sortOrder){
        validateNum(sortOrder, "정렬 순서는 0 이상이어야 합니다.");

        this.sortOrder = sortOrder;
    }

    private static void validateNum(Long infoNum, String s) {
        if (infoNum == null || infoNum < 0) {
            throw new IllegalArgumentException(s);
        }
    }

    private static void validateNpc(Npc npc) {
        if (npc == null) {
            throw new IllegalArgumentException("NPC는 필수입니다.");
        }
    }

    private static void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("아이템은 필수입니다.");
        }
    }

//    public void increaseQuantity(Long quantity){
//        if(quantity == null || quantity <= 0){
//            throw new IllegalArgumentException("증가 수량은 1 이상이어야 합니다.");
//        }
//
//        this.quantity += quantity;
//    }
//
//    public void decreaseQuantity(Long quantity){
//        if(quantity == null || quantity <= 0){
//            throw new IllegalArgumentException("차감 수량은 1 이상이어야 합니다.");
//        }
//
//        if(this.quantity < quantity){
//            throw new IllegalArgumentException("판매 가능 수량보다 많이 차감할 수 없습니다.");
//        }
//
//        this.quantity -= quantity;
//    }



}
