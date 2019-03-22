package cloud.forum.rest;

import cloud.forum.domain.Forum;
import cloud.forum.domain.Post;
import cloud.forum.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity createForum(@RequestBody Post post){
        Post result = postService.createPost(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/forum/{id}/posts")
    public ResponseEntity<Page<Post>> getForumPosts(@PathVariable(name = "id") Forum forum, Pageable page) {
        return ResponseEntity.ok(postService.findByForum(forum,page));
    }
}
