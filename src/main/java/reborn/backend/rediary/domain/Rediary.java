package reborn.backend.rediary.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import reborn.backend.global.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rediary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rediaryTitle;

    private String rediaryContents;


    private LocalDateTime rediaryCreatedAt;

    @Enumerated(EnumType.STRING)
    private EmotionStatus emotionStatus;

    public enum EmotionStatus {
        SUNNY, CLOUDY, RAINY
    }

}
