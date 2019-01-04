package cloud.forum.rest;

import cloud.forum.domain.ForumUser;
import cloud.forum.repository.ForumUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ForumUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(ForumUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user")
    public ResponseEntity<ForumUser> createUser(@RequestBody ForumUser user) {
        String password = user.getPassword();
        password = passwordEncoder.encode(password);
        ForumUser newUser = new ForumUser(user.getUsername(), password, user.isEnabled(), user.getRoles());
        ForumUser forumUser = userRepository.save(newUser);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(forumUser);
    }
}
