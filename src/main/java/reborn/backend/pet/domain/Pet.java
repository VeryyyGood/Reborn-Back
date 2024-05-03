package reborn.backend.pet.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import reborn.backend.global.entity.BaseEntity;
import reborn.backend.global.entity.PetColor;
import reborn.backend.global.entity.PetType;
import reborn.backend.global.entity.RebornType;
import reborn.backend.reborn_15._2_remind.domain.Remind;
import reborn.backend.reborn_15._3_reveal.domain.Reveal;
import reborn.backend.reborn_15._4_remember.domain.Remember;
import reborn.backend.reborn_15._5_reborn.domain.Reborn;
import reborn.backend.user.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String petName;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate anniversary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetType petType;

    @Column(nullable = false)
    private String detailPetType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetColor petColor;

    @Column(nullable = false)
    private String progressState;

    @Column
    private Integer rebornDate;

    @Enumerated(EnumType.STRING)
    @Column
    private RebornType rebornType;

    // 나의 반려견 15일 컨텐츠 기록 열람시 필요
    @OneToMany(mappedBy = "pet")
    private List<Remind> remindList = new ArrayList<>();

    @OneToMany(mappedBy = "pet")
    private List<Reveal> revealList = new ArrayList<>();

    @OneToMany(mappedBy = "pet")
    private List<Remember> rememberList = new ArrayList<>();

    @OneToMany(mappedBy = "pet")
    private List<Reborn> rebornList = new ArrayList<>();

}