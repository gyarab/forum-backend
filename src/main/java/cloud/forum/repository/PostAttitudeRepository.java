package cloud.forum.repository;


import cloud.forum.domain.LemonUser;
import cloud.forum.domain.Post;
import cloud.forum.domain.PostAttitude;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostAttitudeRepository extends JpaRepository<PostAttitude, Long> {

    Page<PostAttitude> findAllByOwner(LemonUser owner, Pageable pageable);

    Page<PostAttitude> findAllByPost(Post post, Pageable pageable);

    Optional<PostAttitude> findByOwnerAndPost(LemonUser owner, Post post);
}
