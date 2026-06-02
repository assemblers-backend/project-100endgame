package io.assemblers.project100endgame.npc.domain;

import io.assemblers.project100endgame.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="npcs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Npc extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "r_id", nullable = false, unique = true, length = 100)
    private String rId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 100)
    private String locationKey;

    @Column(nullable = false)
    private boolean active;

    public static Npc create(
            String rId,
            String name,
            String description,
            String locationKey
    ){

        validateRId(rId);
        validateInfo(name,description,locationKey);

        Npc npc = new Npc();

        npc.rId = rId;
        npc.name = name;
        npc.description = description;
        npc.locationKey = locationKey;
        npc.active = true;

        return npc;
    }

    public void update(
            String name,
            String description,
            String locationKey
    ){
        validateInfo(name,description,locationKey);

        this.name = name;
        this.description = description;
        this.locationKey = locationKey;
    }

    public void activate(){
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
    }

    private static void validateRId(String rId) {
        if (rId == null || rId.isBlank()) {
            throw new IllegalArgumentException("NPC 리소스 ID는 필수입니다.");
        }

        if (rId.length() > 100) {
            throw new IllegalArgumentException("NPC 리소스 ID는 100자를 초과할 수 없습니다.");
        }
    }

    private static void validateInfo(
            String name,
            String description,
            String locationKey
    ){

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("NPC 이름은 필수입니다.");
        }

        if (name.length() > 100) {
            throw new IllegalArgumentException("NPC 이름은 100자를 초과할 수 없습니다.");
        }

        if(description != null && description.length() >500){
            throw new IllegalArgumentException("NPC 설명은 500자를 초과할 수 없습니다.");
        }

        if(locationKey == null || locationKey.isBlank()){
            throw new IllegalArgumentException("NPC 위치 키는 필수입니다.");
        }

        if (locationKey.length() > 100) {
            throw new IllegalArgumentException("NPC 위치 키는 100자를 초과할 수 없습니다.");
        }

    }
}
