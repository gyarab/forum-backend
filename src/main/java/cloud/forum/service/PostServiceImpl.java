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

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).isPresent()?  postRepository.findById(id).get() : null;
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post like(Post post) {
        long likes = post.getLikes() + 1;
        post.setLikes(likes);
        return postRepository.saveAndFlush(post);
    }

    @Override
    public Post dislike(Post post) {
        long dislikes = post.getDislikes() + 1;
        post.setDislikes(dislikes);
        return postRepository.saveAndFlush(post);
    }


}
