package cloud.forum.rest;

import cloud.forum.domain.Comment;
import cloud.forum.domain.Post;
import cloud.forum.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PutMapping("/update/like/{commentId}")
    public ResponseEntity<Comment> likeComment(@PathVariable("commentId") Comment comment) {
        Comment result = commentService.like(comment);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/dislike/{commentId}")
    public ResponseEntity<Comment> dislikeComment(@PathVariable("commentId") Comment comment) {
        Comment result = commentService.dislike(comment);
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
    public ResponseEntity<Page<Comment>> commentsByPost(@PathVariable("postId")Post post, Pageable pageable) {
        Page<Comment> comments = commentService.getCommentByPost(post, pageable);
        return ResponseEntity.ok(comments);
    }
}
