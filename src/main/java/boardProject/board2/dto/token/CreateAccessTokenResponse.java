package boardProject.board2.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jmx.export.annotation.ManagedNotification;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccessTokenResponse {
    private String accessToken;
}
