package cloud.forum.repository;

import cloud.forum.domain.ForumUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ForumUserRepository extends CrudRepository<ForumUser, Long> {
    Optional<ForumUser> findByUsername(String username);
}
