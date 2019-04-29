package cloud.forum.service;

import cloud.forum.domain.LemonUser;
import com.naturalprogrammer.spring.lemon.LemonService;
import org.springframework.stereotype.Service;

@Service
public class ForumLemonService extends LemonService<LemonUser, Long> {
    @Override
    public LemonUser newUser() {
        return new LemonUser();
    }

    @Override
    public Long toId(String id) {
        return Long.valueOf(id);
    }

    @Override
    protected LemonUser createAdminUser() {
        LemonUser adminUser = super.createAdminUser();
        adminUser.setName("Admin");
        return adminUser;
    }
}
