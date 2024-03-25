package reborn.backend.user.jwt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    private String id;

    // private String ip;

    //@ElementCollection
    //private Collection<String> authorities; // 권한을 문자열 형태로 저장
    //private Collection<? extends GrantedAuthority> authorities;

    private String refreshToken;

    private Long ttl;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateTtl(Long ttl) {
        this.ttl = ttl;
    }
}