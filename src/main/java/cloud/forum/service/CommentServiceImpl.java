package cloud.forum.service;

import cloud.forum.domain.Comment;
import cloud.forum.domain.Post;
import cloud.forum.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Comment> getCommentByPost(Post post, Pageable pageable) {
        return repository.findAllByPost(post, pageable);
    }

    @Override
    public Comment like(Comment comment) {
        long likes = comment.getLikes() + 1;
        comment.setLikes(likes);
        return repository.saveAndFlush(comment);
    }

    @Override
    public Comment dislike(Comment comment) {
        long dislikes = comment.getDislikes() + 1;
        comment.setDislikes(dislikes);
        return repository.saveAndFlush(comment);
    }

    @Override
    public Comment createComment(Comment comment) {
        return repository.save(comment);
    }
}
