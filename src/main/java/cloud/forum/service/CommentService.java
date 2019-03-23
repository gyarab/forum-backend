package cloud.forum.service;

import cloud.forum.domain.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CommentService {
    List<Comment> getCommentByPost(Long id);
}
