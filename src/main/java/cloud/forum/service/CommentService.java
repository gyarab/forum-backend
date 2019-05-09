package cloud.forum.service;

import cloud.forum.dataTransferObjects.CommentAttitudeDto;
import cloud.forum.domain.Comment;
import cloud.forum.domain.LemonUser;
import cloud.forum.domain.Post;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Page<CommentAttitudeDto> getCommentByPost(Post post, UserDto user, Pageable pageable);

    CommentAttitudeDto like(Comment comment, LemonUser user);

    CommentAttitudeDto dislike(Comment comment, LemonUser user);

    CommentAttitudeDto createComment(Comment result, UserDto user);
}
