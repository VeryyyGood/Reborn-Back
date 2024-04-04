package reborn.backend.rediary.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import reborn.backend.global.entity.BaseEntity;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rediary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rediaryId;

    @Column(length = 20, nullable = false)
    private String rediaryWriter;

    @Column(nullable = false)
    private String rediaryTitle;

    @Column(nullable = false)
    private LocalDate rediaryCreatedAt;

    @Column(nullable = false)
    private String rediaryContents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PickEmotion pickEmotion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultEmotion resultEmotion;
}
