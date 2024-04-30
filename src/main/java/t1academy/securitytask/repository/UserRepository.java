package t1academy.securitytask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import t1academy.securitytask.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(
            String username
    );

    Optional<User> findByUsername(
            String username
    );

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username and u.email=:email")
    Boolean existsByUser(String username, String email);


}
