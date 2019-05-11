package cloud.forum.service;

import cloud.forum.dataTransferObjects.PostAttitudeDto;
import cloud.forum.domain.Forum;
import cloud.forum.domain.LemonUser;
import cloud.forum.domain.Post;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostAttitudeDto> findByForum(Forum forum, UserDto user, Pageable page);

    PostAttitudeDto findById(Long id, UserDto userDto);

    PostAttitudeDto like(Post post, LemonUser user);

    PostAttitudeDto dislike(Post post,LemonUser user);

    PostAttitudeDto createPost(Post post, UserDto user);

    void deletePost(Post post, LemonUser lemonUser);
}
