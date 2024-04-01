package reborn.backend.pet.domain;

import jakarta.persistence.*;
import lombok.*;
import reborn.backend.user.domain.User;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pet")
public class Pet{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String petName;

    @Column(nullable = false)
    private LocalDate anniversary;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Column(nullable = false)
    private String detailPetType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetColor petColor;

    @Column
    private String petImage;
}