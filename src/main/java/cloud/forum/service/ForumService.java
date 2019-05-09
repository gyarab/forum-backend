package cloud.forum.service;

import cloud.forum.domain.Forum;
import cloud.forum.domain.LemonUser;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ForumService {

    Forum createForum(Forum forum);

    List<Forum> getForumByParent(String parent);
    List<Forum> findAll();

    Forum findForumById(Long id);

    Map<String,Long> getAllForumNames();

    Map<String, Long> searchByTitle(String forumTitle);

    Forum createForum(Forum forum, UserDto user);

    void deleteForum(Forum forum, LemonUser lemonUser);
}
