package reborn.backend.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import reborn.backend.global.entity.BaseEntity;
import reborn.backend.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board extends BaseEntity {
    /*
    1. EmotionShareBoard
    2. ActivityShareBoard
    3. ProductShareBoard
    4. ChatShareBoard
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, columnDefinition = "varchar(45) default 'ACTIVE'")
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column
    private Integer likeCount;

    @Column(length = 5000, nullable = false)
    private String boardContent;

    @Column(nullable = false)
    private Integer imageAttached;

    @Column
    private String boardImage;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

}