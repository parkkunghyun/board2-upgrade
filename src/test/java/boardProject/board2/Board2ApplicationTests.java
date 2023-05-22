package boardProject.board2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // created , updated_at 자동 업데이트!
@SpringBootTest
class Board2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
