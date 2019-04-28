package cloud.forum.rest;

import cloud.forum.dataTransferObjects.CommentAttitudeDto;
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
    public ResponseEntity<CommentAttitudeDto> likeComment(@PathVariable("commentId") Comment comment,
                                                          @AuthenticationPrincipal(expression = "currentUser()") UserDto user) {
        LemonUser lemonUser = lemonService.findUserById(user.getId()).orElseThrow(IllegalStateException::new);
//        LecwUtils.currentUser()
        CommentAttitudeDto result = commentService.like(comment, lemonUser);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/dislike/{commentId}")
    public ResponseEntity<CommentAttitudeDto> dislikeComment(@PathVariable("commentId") Comment comment,
                                                  @AuthenticationPrincipal (expression = "currentUser()") UserDto user) {
        LemonUser lemonUser = lemonService.findUserById(user.getId()).orElseThrow(IllegalStateException::new);
        CommentAttitudeDto result = commentService.dislike(comment, lemonUser);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create/{postId}")
    public ResponseEntity createComment(@PathVariable("postId") Post post,
                                        @RequestBody Comment comment) {
        Comment result =comment;
         result.setPost(post);
               result =  commentService.createComment(result);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/{postId}/post")
    public ResponseEntity<Page<CommentAttitudeDto>> commentsByPost(@PathVariable("postId") Post post, Pageable pageable) {
        UserDto user = LecwUtils.currentUser();
        Page<CommentAttitudeDto> comments = commentService.getCommentByPost(post,user, pageable);
        return ResponseEntity.ok(comments);
    }
}
