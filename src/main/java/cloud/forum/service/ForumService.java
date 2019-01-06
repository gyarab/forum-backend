package cloud.forum.service;

import cloud.forum.domain.Forum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ForumService {
    Forum createForum(Forum forum);
    List<Forum> getForumByParent(String parent);
    List<Forum> findAll();
    Forum findForumById(Long id);

    Page<Forum> findAll(Pageable page);
}
