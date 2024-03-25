package reborn.backend.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import reborn.backend.global.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pet")
public class Pet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(nullable = false)
    private String petname;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime anniversary;

    @NotNull
    @Column(nullable = false)
    private PetType petType;

    @NotNull
    @Column(nullable = false)
    private String detailPetType;

    @NotNull
    @Column(nullable = false)
    private PetColor petColor;

    @Column
    private String petImage;
}