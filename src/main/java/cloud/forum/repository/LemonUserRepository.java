package cloud.forum.repository;

import cloud.forum.domain.LemonUser;
import com.naturalprogrammer.spring.lemon.domain.AbstractUserRepository;

public interface LemonUserRepository extends AbstractUserRepository<LemonUser, Long> {
}
