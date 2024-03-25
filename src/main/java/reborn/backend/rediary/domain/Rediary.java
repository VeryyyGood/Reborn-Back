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

    private String rediaryTitle;

    private String rediaryContents;

    @Enumerated(EnumType.STRING)
    private EmotionStatus emotionStatus;
}
