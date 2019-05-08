package cloud.forum.service;

import cloud.forum.domain.Forum;
import cloud.forum.domain.LemonUser;
import cloud.forum.repository.ForumRepository;
import com.naturalprogrammer.spring.lemon.LemonService;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("forumService")
public class ForumServiceImpl implements ForumService {

    private final ForumRepository repository;
    private final LemonService<LemonUser, Long> lemonService;

    public ForumServiceImpl(ForumRepository repository, LemonService lemonService) {
        this.repository = repository;
        this.lemonService = lemonService;
    }

    @Override
    public Forum createForum(Forum forum) {
        return repository.save(forum);
    }

    @Override
    public Forum createForum(Forum forum, UserDto user) {
        return lemonService.findUserById(user.getId())
                .map(u -> {
                    forum.setUser(u);
                    return forum;
                })
                .map(repository::saveAndFlush)
                .orElseThrow(() -> new IllegalArgumentException("User is required"));
    }

    @Override
    public List<Forum> getForumByParent(String parent) {
        return repository.findAllByParent(parent);
    }

    @Override
    public List<Forum> findAll() {
        return repository.findAll();
    }

    @Override
    public Forum findForumById(Long id) {
        return repository.findById(id).isPresent() ? repository.findById(id).get() : null;
    }

    @Override
    public Map<String, Long> getAllForumNames() {
        Map<String, Long> result = new HashMap<>();
        repository.findAll().forEach(forum -> {
            result.put(forum.getName(), forum.getId());
        });
        return result;
    }

    @Override
    public Map<String, Long> searchByTitle(String forumName) {
        Map<String, Long> result = new HashMap<>();
        repository.findForumsByNameContaining(forumName).forEach(forum -> {
            result.put(forum.getName(), forum.getId());
        });
        return result;
    }

//    @Override
//    public Page<Forum> findAll(Pageable page) {
//        return repository.findAll(page);
//    }
}
