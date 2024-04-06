package reborn.backend.reveal.domain;

import jakarta.persistence.*;
import lombok.*;
import reborn.backend.global.entity.BaseEntity;
import reborn.backend.global.entity.PickEmotion;
import reborn.backend.global.entity.ResultEmotion;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reveal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long revealId;

    @Column(length = 20, nullable = false)
    private String revealWriter;

    @Column(nullable = false)
    private Integer revealCreatedAt;

    @Column(nullable = false)
    private String revealContents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PickEmotion pickEmotion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultEmotion resultEmotion;
}
