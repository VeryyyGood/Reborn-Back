package reborn.backend.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reborn.backend.global.entity.BaseEntity;
import reborn.backend.user.domain.User;

import java.sql.ConnectionBuilder;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardBookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_id")
    private Board board;
}
