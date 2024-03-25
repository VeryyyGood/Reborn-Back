package reborn.backend.user.domain;

import jakarta.persistence.*;
import lombok.*;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.Comment;
import reborn.backend.global.entity.BaseEntity;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Naver 로그인 제공자 문자값
    private String provider;
    // Naver에서 사용자를 식별하기 위해 제공한 값
    private String providerId;

    @OneToMany(mappedBy = "user")
    private List<Pet> pets = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    // rediary 일대다 매핑 추가
}
