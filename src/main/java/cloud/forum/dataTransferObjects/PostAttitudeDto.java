package cloud.forum.dataTransferObjects;

import cloud.forum.domain.LemonUser;
import cloud.forum.domain.Post;
import cloud.forum.domain.enums.Attitude;
import cloud.forum.repository.LemonUserRepository;
import com.naturalprogrammer.spring.lemon.commonsweb.util.LecwUtils;
import lombok.Getter;

@Getter
public class PostAttitudeDto {
    private AttitudeDto attitudeDto;
    private Post post;
    public PostAttitudeDto(Long lemonUserId, String name, Attitude attitude, Post post) {
        attitudeDto = new AttitudeDto(lemonUserId,attitude,name);
        this.post = post;

    }
}
