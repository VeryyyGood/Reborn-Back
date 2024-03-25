package reborn.backend.rediary.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import reborn.backend.global.entity.BaseEntity;

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

    @Column
    private String rediaryTitle;

    @Column
    private String rediaryContents;

    @Enumerated(EnumType.STRING)
    @Column
    private EmotionStatus emotionStatus;
}
