package cloud.forum.rest;

import cloud.forum.domain.Forum;
import cloud.forum.domain.Post;
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
/*
 * Main Rest controller, configures REST API
 */
@RestController
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;

    public ForumController(ForumService forumService, PostService postService) {
        this.forumService = forumService;
    }

    //Upon calling /forum/all get all forum names in the database.
    @GetMapping("/all")
    public ResponseEntity<Map<String,Long>> getAllForumNames() {
        return ResponseEntity.ok(forumService.getAllForumNames());
    }


    //Create a forum when the frontend sends a Forum class to the /forum/create url
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity createForum(@RequestBody Forum forum) {
        Forum result = forumService.createForum(forum);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Map<String, Long>> searchForumsByTitle(@PathVariable(name="name") String forumTitle){
        return ResponseEntity.ok(forumService.searchByTitle(forumTitle));
    }

}
