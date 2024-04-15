package reborn.backend.reborn_15._4_remember.domain;

import jakarta.persistence.*;
import lombok.*;
import reborn.backend.pet.domain.Pet;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Remember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column
    private Integer date;

    @Column
    private String rememberImage;

    @Column
    private String content;

    @Column
    private Boolean pat;

    @Column
    private Boolean feed;

    @Column
    private Boolean walk;

    @Column
    private Boolean snack;

    @Column
    private Boolean clean_1;

    @Column
    private Boolean clean_2;

    @Column
    private Boolean clean_3;

    @Column
    private Boolean clean_4;

    @Column
    private Boolean clean_5;

    @Column
    private Boolean clean_6;

}
