package cloud.forum.rest;

import cloud.forum.domain.Comment;
import cloud.forum.domain.LemonUser;
import cloud.forum.domain.Post;
import cloud.forum.service.CommentService;
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
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final LemonService<LemonUser, Long> lemonService;

    public CommentController(CommentService commentService, LemonService lemonService) {
        this.commentService = commentService;
        this.lemonService = lemonService;
    }

    @PutMapping("/update/like/{commentId}")
    public ResponseEntity<Comment> likeComment(@PathVariable("commentId") Comment comment,
                                               @AuthenticationPrincipal(expression = "currentUser()") UserDto user) {
        LemonUser lemonUser = lemonService.findUserById(user.getId()).orElseThrow(IllegalStateException::new);
//        LecwUtils.currentUser()
        Comment result = commentService.like(comment, lemonUser);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/dislike/{commentId}")
    public ResponseEntity<Comment> dislikeComment(@PathVariable("commentId") Comment comment,
                                                  @AuthenticationPrincipal (expression = "currentUser()") UserDto user) {
        LemonUser lemonUser = lemonService.findUserById(user.getId()).orElseThrow(IllegalStateException::new);
        Comment result = commentService.dislike(comment, lemonUser);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody Comment comment) {
        Comment result = commentService.createComment(comment);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(comment);
    }

    @GetMapping("/{postId}/post")
    public ResponseEntity<Page<Comment>> commentsByPost(@PathVariable("postId") Post post, Pageable pageable) {
        Page<Comment> comments = commentService.getCommentByPost(post, pageable);
        return ResponseEntity.ok(comments);
    }
}
