package cloud.forum.rest;

import cloud.forum.domain.Forum;
import cloud.forum.domain.LemonUser;
import cloud.forum.service.ForumService;
import cloud.forum.service.PostService;
import com.naturalprogrammer.spring.lemon.LemonService;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commonsweb.util.LecwUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
/*
 * Main Rest controller, configures REST API
 */
@RestController
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;
    private final LemonService<LemonUser, Long> lemonService;

    public ForumController(ForumService forumService, PostService postService, LemonService<LemonUser, Long> lemonService) {
        this.forumService = forumService;
        this.lemonService = lemonService;
    }

    //Upon calling /forum/all get all forum names in the database.
    @GetMapping("/all")
    public ResponseEntity<Map<String,Long>> getAllForumNames() {
        return ResponseEntity.ok(forumService.getAllForumNames());
    }


    //Create a forum when the frontend sends a Forum object to the /forum/create url
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity<Map<String,Long>> createForum(@RequestBody Forum forum) {
        UserDto user = LecwUtils.currentUser();
        Forum result = forumService.createForum(forum,user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId()).toUri();
        Map<String, Long> map = new HashMap<>();
        map.put(result.getName(),result.getId());
        return ResponseEntity.created(location).body(map);
    }
    @DeleteMapping("/delete/{forumId}")
    public ResponseEntity deletePost(@PathVariable("forumId") Forum forum){
        UserDto user = LecwUtils.currentUser();
        LemonUser lemonUser = lemonService.findUserById(user.getId()).orElseThrow(IllegalStateException::new);
        forumService.deleteForum(forum,lemonUser);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<Map<String, Long>> searchForumsByTitle(@PathVariable(name="name") String forumTitle){
        return ResponseEntity.ok(forumService.searchByTitle(forumTitle));
    }

}
