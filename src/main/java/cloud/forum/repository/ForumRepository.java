package cloud.forum.repository;

import cloud.forum.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {

    List<Forum>findAllByParent(String parent);

}
