package cloud.forum.rest;

import cloud.forum.domain.Forum;
import cloud.forum.service.ForumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

//Main Rest controller, configures REST API
@RestController
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }
    //Upon calling /forum/all get all forums in the database.
    @GetMapping("/all")
    public ResponseEntity<List<Forum>> getAllForums() {
        return ResponseEntity.ok(forumService.findAll());
    }

    //Create a forum when the frontend sends a Forum class to the /forum/create url
    @PostMapping("/create")
    public ResponseEntity createForum(@RequestBody Forum forum){
        Forum result = forumService.createForum(forum);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }



}
