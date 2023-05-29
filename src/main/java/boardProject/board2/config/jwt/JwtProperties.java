package boardProject.board2.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("jwt") // 자바 클래스에서 프러퍼티값을 가져와서 사용하는 어노테이션
// *.properties , *.yml 파일에 있는 property를 자바 클래스에 값을 가져와서(바인딩) 사용할 수 있게 해주는 어노테이션
public class JwtProperties {
    // 해당 값들을 변수로 접근하는데 사용할 클래스!
    private String issuer;
    private String secretKey;

}
