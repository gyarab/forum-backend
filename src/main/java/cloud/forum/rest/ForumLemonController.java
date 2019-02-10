package cloud.forum.rest;

import cloud.forum.domain.LemonUser;
import com.naturalprogrammer.spring.lemon.LemonController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
@RequestMapping("/api/core")
public class ForumLemonController extends LemonController<LemonUser, Long> {
}
