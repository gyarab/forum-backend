package cloud.forum.service;

import cloud.forum.domain.Comment;
import cloud.forum.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Comment> getCommentByPost(Long id) {
        return null;
    }
}
