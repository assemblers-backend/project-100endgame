package io.assemblers.project100endgame.inventory.entity;

import io.assemblers.project100endgame.common.entity.BaseEntity;
import io.assemblers.project100endgame.item.entity.Item;
import io.assemblers.project100endgame.user.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private boolean equipped;

    @Column(nullable = false)
    private LocalDateTime acquiredAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Builder
    public UserItem(
            Users user,
            Item item,
            Long quantity
    ){
        validateUser(user);
        validateItem(item);
        validateQuantity(quantity);

        this.user = user;
        this.item = item;
        this.quantity = quantity;
        this.equipped = false;
        this.acquiredAt = LocalDateTime.now();

    }

    public void increaseQuantity(Long quantity) {
        validateQuantity(quantity);

        this.quantity += quantity;
    }

    public void decreaseQuantity(Long quantity) {
        validateQuantity(quantity);

        if (this.quantity < quantity) {
            throw new IllegalArgumentException("보유 수량보다 많이 감소시킬 수 없습니다.");
        }

        this.quantity -= quantity;
    }

    public void equip() {
        this.equipped = true;
    }

    public void unequip() {
        this.equipped = false;
    }

    private static void validateUser(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("유저는 필수입니다.");
        }
    }

    private static void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("아이템은 필수입니다.");
        }
    }

    private static void validateQuantity(Long quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("아이템 수량은 1 이상이어야 합니다.");
        }
    }
}
