package reborn.backend.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import reborn.backend.user.OAuth2SuccessHandler;
import reborn.backend.user.OAuth2UserServiceImpl;
import reborn.backend.user.jwt.AuthCreationFilter;
import reborn.backend.user.jwt.JwtValidationFilter;
import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final AuthCreationFilter authCreationFilter;
    private final JwtValidationFilter jwtValidationFilter;

    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    @Bean
    protected SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authHttp -> authHttp
                        .requestMatchers(
                                "/health",

                                "/oauth2/authorization/naver", // 로그인
                                "/login/oauth2/code/**", // code, state 반환
                                "/token/**", // 로컬에게 토큰 반환
                                "/oauth/**" // 프론트에게 토큰 반환

                        )
                        .permitAll()
                        .anyRequest().permitAll()
                        //.anyRequest().authenticated()

                )
                .oauth2Login(oauth2Login -> oauth2Login
                        //.loginPage("/users/login")
                        .successHandler(oAuth2SuccessHandler) //인증 성공 후 jwt 생성, 사용자 정보 db에 등록
                        //.defaultSuccessUrl("/users/main") //로그인(일정 부분) 성공하면 특정 화면으로 이동
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService) //사용자 데이터 처리
                        )
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(authCreationFilter, AuthorizationFilter.class)
                .addFilterBefore(jwtValidationFilter, AuthCreationFilter.class)
        ;

        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}