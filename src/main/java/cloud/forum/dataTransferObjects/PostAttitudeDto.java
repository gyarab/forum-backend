package cloud.forum.dataTransferObjects;

import cloud.forum.domain.Post;
import cloud.forum.domain.enums.Attitude;
import lombok.Getter;

@Getter
public class PostAttitudeDto {
    private AttitudeDto attitudeDto;
    private Post post;

    public PostAttitudeDto(Long lemonUserid, String name, Attitude attitude, Post post) {
        attitudeDto = new AttitudeDto(lemonUserid,attitude,name);
        this.post = post;
    }
}
