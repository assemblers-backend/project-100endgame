package io.assemblers.project100endgame.item.entity;

import io.assemblers.project100endgame.common.entity.BaseEntity;
import io.assemblers.project100endgame.item.constant.ItemGrade;
import io.assemblers.project100endgame.item.constant.ItemType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="r_id", nullable = false, unique = true, length = 100)
    private String rId;

    @Column(nullable = false, length = 100)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ItemGrade itemGrade;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long sellPrice;

    @Builder
    public Item(
            String rId,
            String itemName,
            ItemType itemType,
            ItemGrade itemGrade,
            String description,
            Long price,
            Long sellPrice
    ){

        validateRId(rId);
        validateInfo(itemName, itemType, itemGrade, description);
        validatePrice(price, sellPrice);

        this.rId = rId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemGrade = itemGrade;
        this.description = description;
        this.price = price;
        this.sellPrice = sellPrice;

    }


    public void updateInfo(
            String itemName,
            ItemType itemType,
            ItemGrade itemGrade,
            String description
    ){
        validateInfo(itemName, itemType, itemGrade, description);

        this.itemName = itemName;
        this.itemType = itemType;
        this.itemGrade = itemGrade;
        this.description = description;

    }

    public void updatePrice(
            Long price,
            Long sellPrice
    ){

        validatePrice(price, sellPrice);

        this.price = price;
        this.sellPrice = sellPrice;
    }

    private static void validateRId(String rId) {
        if (rId == null || rId.isBlank()) {
            throw new IllegalArgumentException("아이템 리소스 ID는 필수입니다.");
        }

        if (rId.length() > 100) {
            throw new IllegalArgumentException("아이템 리소스 ID는 100자를 초과할 수 없습니다.");
        }
    }

    private static void validateInfo(
            String itemName,
            ItemType itemType,
            ItemGrade itemGrade,
            String description
    ) {
        if (itemName == null || itemName.isBlank()) {
            throw new IllegalArgumentException("아이템 이름은 필수입니다.");
        }

        if (itemName.length() > 100) {
            throw new IllegalArgumentException("아이템 이름은 100자를 초과할 수 없습니다.");
        }

        if (itemType == null) {
            throw new IllegalArgumentException("아이템 타입은 필수입니다.");
        }

        if (itemGrade == null) {
            throw new IllegalArgumentException("아이템 등급은 필수입니다.");
        }

        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("아이템 설명은 500자를 초과할 수 없습니다.");
        }
    }

    private static void validatePrice(
            Long price,
            Long sellPrice
    ){
        if(price == null || price < 0){
            throw new IllegalArgumentException("아이템 가격은 0 이상이어야합니다.");
        }

        if (sellPrice == null || sellPrice < 0) {
            throw new IllegalArgumentException("판매 가격은 0 이상이어야 합니다.");
        }

        if (sellPrice > price) {
            throw new IllegalArgumentException("판매 가격은 구매 가격보다 클 수 없습니다.");
        }

    }



}
