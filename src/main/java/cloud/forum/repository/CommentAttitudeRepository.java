package cloud.forum.repository;


import cloud.forum.domain.Comment;
import cloud.forum.domain.CommentAttitude;
import cloud.forum.domain.LemonUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentAttitudeRepository extends JpaRepository<CommentAttitude, Long> {

    Page<CommentAttitude> findAllByOwner(LemonUser owner, Pageable pageable);

    Page<CommentAttitude> findAllByComment(Comment comment, Pageable pageable);

    Optional<CommentAttitude> findByOwnerAndComment(LemonUser owner, Comment comment);
}
