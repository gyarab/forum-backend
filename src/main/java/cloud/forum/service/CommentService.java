package cloud.forum.service;

import cloud.forum.domain.Comment;
import cloud.forum.domain.LemonUser;
import cloud.forum.domain.Post;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Page<Comment> getCommentByPost(Post post, Pageable pageable);

    Comment createComment(Comment comment);

    Comment like(Comment comment, LemonUser user);

    Comment dislike(Comment comment, LemonUser user);
}
