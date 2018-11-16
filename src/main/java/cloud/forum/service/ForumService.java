package cloud.forum.service;

import cloud.forum.domain.Forum;

import java.util.List;

public interface ForumService {
    Forum createForum(Forum forum);
    List<Forum> getForumByParent(String parent);
    List<Forum> findAll();
    Forum findForumById(Long id);

}
