package cloud.forum.service;

import cloud.forum.domain.Forum;
import cloud.forum.domain.Post;
import cloud.forum.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("postService")
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Page<Post> findByForum(Forum forum, Pageable page) {
        return postRepository.findAllByForum(forum, page);
    }
}
