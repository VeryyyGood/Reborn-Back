package reborn.backend.user.domain;

import jakarta.persistence.*;
import lombok.*;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.BoardBookmark;
import reborn.backend.comment.domain.Comment;
import reborn.backend.global.entity.BaseEntity;
import reborn.backend.pet.domain.Pet;
import reborn.backend.rediary.domain.Rediary;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long contentPetId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String profileImage;

    @Column(nullable = true)
    private String backgroundImage;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String deviceToken;

    // Naver 로그인 제공자 문자값
    @Column(nullable = false)
    private String provider;

    @OneToMany(mappedBy = "user")
    private List<Pet> petList = new ArrayList<>();

    // 내가 작성한 게시물 따로 열람해야 하기에
    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    // 내가 북마크한 게시물 따로 열람해야 하기에
    @OneToMany(mappedBy = "user")
    private List<BoardBookmark> boardBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Rediary> rediaryList = new ArrayList<>();
}
