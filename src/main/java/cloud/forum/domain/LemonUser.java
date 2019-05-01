package cloud.forum.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.naturalprogrammer.spring.lemon.commons.util.UserUtils;
import com.naturalprogrammer.spring.lemon.domain.AbstractUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "lemon_users")
public class LemonUser extends AbstractUser<Long> {
    public static final int NAME_MIN = 1;
    public static final int NAME_MAX = 50;

    @JsonView(UserUtils.SignupInput.class)
    @NotBlank(message = "{blank.name}", groups = {UserUtils.SignUpValidation.class, UserUtils.UpdateValidation.class})
    @Size(min=NAME_MIN, max=NAME_MAX, groups = {UserUtils.SignUpValidation.class, UserUtils.UpdateValidation.class})
    @Column(nullable = false,length = NAME_MAX)
    private String name;
    
    public static class Tag implements Serializable {

        private static final long serialVersionUID = -2129078111926834670L;

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public Tag toTag() {

        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }

}
