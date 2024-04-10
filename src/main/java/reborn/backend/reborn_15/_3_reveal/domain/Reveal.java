package reborn.backend.reborn_15._3_reveal.domain;

import jakarta.persistence.*;
import lombok.*;
import reborn.backend.global.entity.BaseEntity;
import reborn.backend.global.entity.PickEmotion;
import reborn.backend.global.entity.ResultEmotion;
import reborn.backend.pet.domain.Pet;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reveal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column
    private Integer date;

    @Column
    private String diaryContent;

    @Enumerated(EnumType.STRING)
    @Column
    private PickEmotion pickEmotion;

    @Enumerated(EnumType.STRING)
    @Column
    private ResultEmotion resultEmotion;

    @Column
    private Boolean pat;

    @Column
    private Boolean feed;

    @Column
    private Boolean walk;

    @Column
    private Boolean snack;
}
