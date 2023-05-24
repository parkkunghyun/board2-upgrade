package boardProject.board2.config;

import boardProject.board2.service.UserDetailService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserDetailService userDetailService;

    // 스프링 시큐리티 기능 비활성화
    // 인증 인가서비스를 모든 곳에모두 적용 안할때!
    // 지금은 static과 h2 콘솔은 시큐리티 적용안함!

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // 특정 http 요청에 대한 웹 기반 보안 구성
    // 인증 인가 로그인 로그아웃 설정!
    // permitAll은 그냥 들어가기 가능!
    // anyRequest() 위에꺼 제외 남은거
    // .authenticated() 인가는 아니고 인증!
    // invalidateHttpSession(true) 로그아웃하고 세션 전체 삭제 할지 여부 설정
    // csrf() 원래는 비활성화가 맞음 수업을 위해 풀어둠..
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .requestMatchers("/login", "/signup", "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/articles")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable().build();
    }

    // 사용자 정보를 가져올 서비스를 재정의하거나 인증방법 고안
    // jdbc 기반 인증 설정할때 사용!
    // 인증 관리자 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder) // 비밀번호 암호화 해줌!
                .and().build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
