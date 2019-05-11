package cloud.forum.rest;

import cloud.forum.dataTransferObjects.PostAttitudeDto;
import cloud.forum.domain.Forum;
import cloud.forum.domain.LemonUser;
import cloud.forum.domain.Post;
import cloud.forum.service.PostService;
import com.naturalprogrammer.spring.lemon.LemonService;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commonsweb.util.LecwUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "*")
/*
 * Post controller, configures REST API
 */
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private final LemonService<LemonUser, Long> lemonService;

    public PostController(PostService postService, LemonService<LemonUser, Long> lemonService) {
        this.postService = postService;
        this.lemonService = lemonService;
    }

    @PostMapping("/create/{forumId}")
    public ResponseEntity<PostAttitudeDto> createPost(@PathVariable("forumId") Forum forum, @RequestBody Post post) {
        UserDto user = LecwUtils.currentUser();
        post.setForum(forum);
        PostAttitudeDto result = postService.createPost(post,user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getPost().getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity deletePost(@PathVariable("postId") Post post){
        UserDto user = LecwUtils.currentUser();
        LemonUser lemonUser = lemonService.findUserById(user.getId()).orElseThrow(IllegalStateException::new);
        postService.deletePost(post,lemonUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/like/{postId}")
    public ResponseEntity<PostAttitudeDto> likePost(@PathVariable("postId") Post post,
                                                    @AuthenticationPrincipal(expression = "currentUser()") UserDto user) {
        LemonUser lemonUser = lemonService.findUserById(user.getId()).orElseThrow(IllegalStateException::new);
        PostAttitudeDto result = postService.like(post, lemonUser);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/dislike/{postId}")
    public ResponseEntity<PostAttitudeDto> dislikePost(@PathVariable("postId") Post post,
                                                       @AuthenticationPrincipal(expression = "currentUser()") UserDto user) {
        LemonUser lemonUser = lemonService.findUserById(user.getId()).orElseThrow(IllegalStateException::new);
        PostAttitudeDto result = postService.dislike(post, lemonUser);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PostAttitudeDto> getPostById(@PathVariable(name = "id") Long id) {
        UserDto user = LecwUtils.currentUser();
        return ResponseEntity.ok(postService.findById(id,user));
    }

    @GetMapping("/forum/{id}/posts")
    public ResponseEntity<Page<PostAttitudeDto>> getForumPosts(@PathVariable(name = "id") Forum forum,
                                                               Pageable page) {
        UserDto user = LecwUtils.currentUser();
        return ResponseEntity.ok(postService.findByForum(forum, user, page));
    }
}
