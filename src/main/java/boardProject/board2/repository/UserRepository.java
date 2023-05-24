package boardProject.board2.repository;

import boardProject.board2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String Email);
    // 사용자 정보를 가져오기 위해서는 스프링 시큐리티가 이메일을 전달 받아야함!
    //
}
