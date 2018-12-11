package cloud.forum.rest;

import cloud.forum.domain.ForumUser;
import cloud.forum.repository.ForumUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void createUser(@RequestBody ForumUser user) {
        String password = user.getPassword();
        password = passwordEncoder.encode(password);
        User newUser = new ForumUser(user.getUsername(), password, user.isEnabled(), user.getAuthorities());
        ForumUser forumUser = userRepository.save(user);
    }
}
