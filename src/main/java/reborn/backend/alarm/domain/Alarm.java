package reborn.backend.alarm.domain;

import jakarta.persistence.*;
import lombok.*;
import reborn.backend.global.entity.BaseEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alarm")
public class Alarm{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
