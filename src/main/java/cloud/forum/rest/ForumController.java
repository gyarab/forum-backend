package cloud.forum.rest;

import cloud.forum.domain.Comment;
import cloud.forum.domain.Forum;
import cloud.forum.domain.Post;
import cloud.forum.repository.PostRepository;
import cloud.forum.service.ForumService;
import cloud.forum.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@CrossOrigin(origins = "*")
/**
 * Main Rest controller, configures REST API
 */
@RestController()
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;
    private final PostService postService;

    public ForumController(ForumService forumService, PostService postService) {
        this.forumService = forumService;
        this.postService = postService;
    }

    //Upon calling /forum/all get all forum names in the database.
    @GetMapping("/all")
    public ResponseEntity<Map<String,Long>> getAllForumNames() {
        return ResponseEntity.ok(forumService.getAllForumNames());
    }


    //Create a forum when the frontend sends a Forum class to the /forum/create url
    @PostMapping(value = "/create/forum",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity createForum(@RequestBody Forum forum) {
        Forum result = forumService.createForum(forum);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PostMapping("/create/post")
    public ResponseEntity createForum(@RequestBody Post post){
        Post result = postService.createPost(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<Page<Post>> getForumPosts(@PathVariable(name = "id") Forum forum, Pageable page) {
        return ResponseEntity.ok(postService.findByForum(forum,page));
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<Map<String, Long>> searchForumsByTitle(@PathVariable(name="name") String forumTitle){
        return ResponseEntity.ok(forumService.searchByTitle(forumTitle));
    }
//    public ResponseEntity createComment(@RequestBody Comment comment){
//
//    }

}
