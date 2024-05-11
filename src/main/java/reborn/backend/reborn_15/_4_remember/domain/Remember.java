package reborn.backend.reborn_15._4_remember.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import reborn.backend.pet.domain.Pet;

import java.time.LocalDate;

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
    private String title;

    @Column
    private String content;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate imageDate;

    @Column
    private Boolean pat;

    @Column
    private Boolean feed;

    @Column
    private Boolean walk;

    @Column
    private Boolean snack;

    @Column
    private Boolean clean;

}
