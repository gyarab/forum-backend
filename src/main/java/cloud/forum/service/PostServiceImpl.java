package cloud.forum.service;

import cloud.forum.dataTransferObjects.PostAttitudeDto;
import cloud.forum.domain.Forum;
import cloud.forum.domain.LemonUser;
import cloud.forum.domain.Post;
import cloud.forum.domain.PostAttitude;
import cloud.forum.repository.PostAttitudeRepository;
import cloud.forum.repository.PostRepository;
import com.naturalprogrammer.spring.lemon.LemonService;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static cloud.forum.domain.enums.Attitude.*;

@Service("postService")
@Transactional
public class PostServiceImpl implements PostService {
    private final LemonService<LemonUser, Long> lemonService;
    private final PostRepository postRepository;
    private final PostAttitudeRepository postAttitudeRepository;

    public PostServiceImpl(LemonService<LemonUser, Long> lemonService, PostRepository postRepository, PostAttitudeRepository postAttitudeRepository) {
        this.lemonService = lemonService;
        this.postRepository = postRepository;
        this.postAttitudeRepository = postAttitudeRepository;
    }

    @Override
    public Page<PostAttitudeDto> findByForum(Forum forum, UserDto user, Pageable page) {

        Page<Post> posts = postRepository.findAllByForum(forum, page);

        return Optional.ofNullable(user).map(a -> {
            LemonUser lemonUser = lemonService.findUserById(user.getId()).orElseThrow(IllegalStateException::new);
            return posts.map(post -> postAttitudeRepository.findByOwnerAndPost(lemonUser, post)
                    .map(postAttitude -> new PostAttitudeDto(lemonUser.getId(), lemonUser.getName(), postAttitude.getAttitude(), post))
                    .orElseGet(() -> new PostAttitudeDto(lemonUser.getId(), lemonUser.getName(), NEUTRAL, post)));
        }).orElseGet(() -> posts.map(p -> new PostAttitudeDto(null, null, NEUTRAL, p)));
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).isPresent() ? postRepository.findById(id).get() : null;
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post createPost(Post post, UserDto user) {
        return lemonService.findUserById(user.getId())
                .map(u -> {
                    post.setUser(u);
                    return post;
                })
                .map(postRepository::saveAndFlush)
                .orElseThrow(() -> new IllegalArgumentException("User is required"));
    }

    @Override
    public PostAttitudeDto like(Post post, LemonUser user) {
        PostAttitude postAttitude = postAttitudeRepository.findByOwnerAndPost(user, post)
                .map(a -> {
                    switch (a.getAttitude()) {
                        case LIKE:
                            break;
                        case NEUTRAL:
                            post.setLikes(post.getLikes() + 1);
                            break;
                        case DISLIKE:
                            post.setLikes(post.getLikes() + 1);
                            post.setDislikes(post.getDislikes() - 1);
                            break;
                    }
                    a.setAttitude(LIKE);
                    return a;
                }).orElseGet(() -> {
                    post.setLikes(post.getLikes() + 1);
                    return new PostAttitude(user, post, LIKE);
                });
        postAttitudeRepository.saveAndFlush(postAttitude);
        Post result = postRepository.saveAndFlush(post);

        return new PostAttitudeDto(user.getId(), user.getName(), LIKE, result);
    }

    @Override
    public PostAttitudeDto dislike(Post post, LemonUser user) {
        PostAttitude postAttitude = postAttitudeRepository.findByOwnerAndPost(user, post)
                .map(a -> {
                    switch (a.getAttitude()) {
                        case DISLIKE:
                            break;
                        case NEUTRAL:
                            post.setDislikes(post.getDislikes() + 1);
                            break;
                        case LIKE:
                            post.setDislikes(post.getDislikes() + 1);
                            post.setLikes(post.getLikes() - 1);
                            break;
                    }
                    a.setAttitude(DISLIKE);
                    return a;
                }).orElseGet(() -> {
                    post.setDislikes(post.getDislikes() + 1);
                    return new PostAttitude(user, post, DISLIKE);
                });
        postAttitudeRepository.saveAndFlush(postAttitude);
        Post result = postRepository.saveAndFlush(post);
        return new PostAttitudeDto(user.getId(), user.getName(), DISLIKE, result);

    }


}
