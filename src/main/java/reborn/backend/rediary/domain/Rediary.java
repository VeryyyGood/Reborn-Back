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

    @Column
    private String rediaryTitle;

    @Column
    private LocalDate rediaryCreatedAt;

    @Column
    private String rediaryContents;

    @Enumerated(EnumType.STRING)
    @Column
    private EmotionStatus emotionStatus;
}
