package cloud.forum.repository;

import cloud.forum.domain.ForumUser;
import org.springframework.data.repository.CrudRepository;

public interface ForumUserRepository extends CrudRepository<ForumUser,Long> {
}
