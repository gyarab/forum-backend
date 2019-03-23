package cloud.forum.service;

import cloud.forum.domain.Forum;
import cloud.forum.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<Post> findByForum(Forum forum, Pageable page);
    Post findById(Long id);
    Post createPost(Post post);
}
