package cloud.forum.service;

import cloud.forum.domain.Forum;
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
//    Page<Forum> findAll(Pageable page);
}
