package reborn.backend.reborn_15._5_reborn.domain;

import jakarta.persistence.*;
import lombok.*;
import reborn.backend.pet.domain.Pet;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reborn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column
    private Integer date;

    @Column
    private String rebornContent;

    @Enumerated(EnumType.STRING)
    @Column
    private RebornType rebornType;

    @Column
    private Boolean pat;

    @Column
    private Boolean feed;

    @Column
    private Boolean wash;

    @Column
    private Boolean clothe;
}
