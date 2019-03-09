package cloud.forum.service;

import cloud.forum.domain.Forum;
import cloud.forum.repository.ForumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("forumService")
public class ForumServiceImpl implements ForumService {

    private final ForumRepository repository;

    public ForumServiceImpl(ForumRepository repository) {
        this.repository = repository;
    }

    @Override
    public Forum createForum(Forum forum) {
        return repository.save(forum);
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
           result.put(forum.getName(),forum.getId());
       });
       return result;
    }

    @Override
    public Map<String, Long> searchByTitle(String forumName) {
        Map<String, Long> result = new HashMap<>();
        repository.findForumsByNameContaining(forumName).forEach(forum ->{
            result.put(forum.getName(), forum.getId());
        });
        return result;
    }

//    @Override
//    public Page<Forum> findAll(Pageable page) {
//        return repository.findAll(page);
//    }
}
