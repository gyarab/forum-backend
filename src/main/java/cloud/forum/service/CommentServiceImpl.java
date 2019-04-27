package cloud.forum.service;

import cloud.forum.domain.Comment;
import cloud.forum.domain.Post;
import cloud.forum.repository.CommentRepository;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
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
    public Comment like(Comment comment, LemonUser user) {

        CommentAttitude commentAttitude = attitudeRepository.findByOwnerAndComment(user, comment)
                .map(a -> {
                    switch (a.getAttitude()) {
                        case LIKE:
                            break;
                        case DISLIKE:
                            comment.setDislikes(comment.getDislikes() - 1);
                            comment.setLikes(comment.getLikes() + 1);
                            break;
                        case NEUTRAL:
                            comment.setLikes(comment.getLikes() + 1);
                            break;
                    }
                    a.setAttitude(LIKE);
                    return a;
                })
                .orElseGet(() -> {
                    comment.setLikes(comment.getLikes() + 1);
                    return new CommentAttitude(user, comment, LIKE);
                });
        attitudeRepository.saveAndFlush(commentAttitude);
        return repository.saveAndFlush(comment);
    }

    @Override
    public Comment dislike(Comment comment, LemonUser user) {
        CommentAttitude commentAttitude = attitudeRepository.findByOwnerAndComment(user, comment)
                .map(a -> {
                    switch (a.getAttitude()) {
                        case DISLIKE:
                            break;
                        case LIKE:
                            comment.setLikes(comment.getLikes() - 1);
                            comment.setDislikes(comment.getDislikes() + 1);
                            break;
                        case NEUTRAL:
                            comment.setDislikes(comment.getDislikes() + 1);
                            break;
                    }
                    a.setAttitude(DISLIKE);
                    return a;
                }).orElseGet(() -> {
                    comment.setDislikes(comment.getDislikes() + 1);
                    return new CommentAttitude(user, comment, DISLIKE);
                });
        attitudeRepository.saveAndFlush(commentAttitude);
        return repository.saveAndFlush(comment);
    }

    @Override
    public Comment createComment(Comment comment) {
        return repository.save(comment);
    }
}
