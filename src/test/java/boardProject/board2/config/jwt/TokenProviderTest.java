package boardProject.board2.config.jwt;

import boardProject.board2.entity.User;
import boardProject.board2.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("유저정보와 만료기간을 전달해  토큰을 생성하기!!")
    @Test
    void generateToken() {
        //given
        User testUser = userRepository.save(
                User.builder().email("user@gmail.com").password("test").build());
        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
        // then
        // jjwt 라이브러리를 사용해 토큰을 복호화! 트큰을 만들때 클레임으로 넣어둔 아이디가 given과 같은지 확인
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        Assertions.assertThat(userId).isEqualTo(testUser.getId());
    }

    // valid token 검증 테스트
    @DisplayName("만료된 토큰일때 유효성 검증 실패하기!")
    @Test
    void validToken_invalidToken() {
        //given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis() ) )
                .build()
                .createToken(jwtProperties);
        // when

        boolean result = tokenProvider.validToken(token);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("유효한 토큰이면 성공하기")
    @Test
    void validToken_validToken() {
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        boolean result = tokenProvider.validToken(token);

        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication 검증테스트 => 토큰 기반으로 인증정보가져오기!")
    @Test
    void getAuthentication() {
        String userEmail = "user@gmail.com";
        String token = JwtFactory.builder().subject(userEmail)
                .build().createToken(jwtProperties);

        Authentication authentication = tokenProvider.getAuthentication(token);

        Assertions.assertThat(
                ((UserDetails) authentication.getPrincipal()).getUsername())
                .isEqualTo(userEmail);
    }

    @DisplayName("getuserId=> 토큰으로 유저 아이디 가져오기")
    @Test
    void getUserId() {
        Long userId=1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        Long userIdByToken = tokenProvider.getUserId(token);

        Assertions.assertThat(userIdByToken).isEqualTo(userId);
    }


}
