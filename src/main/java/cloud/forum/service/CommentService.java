package cloud.forum.service;

import cloud.forum.domain.Comment;
import cloud.forum.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Page<Comment> getCommentByPost(Post post, Pageable pageable);

    Comment like(Comment comment);

    Comment dislike(Comment comment);

    Comment createComment(Comment comment);
}
