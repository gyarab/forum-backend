package cloud.forum.domain;

import com.naturalprogrammer.spring.lemon.domain.AbstractUser;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lemon_users")
public class LemonUser extends AbstractUser<Long> {
}
