package cloud.forum.dataTransferObjects;

import cloud.forum.domain.Comment;
import cloud.forum.domain.enums.Attitude;
import lombok.Getter;

@Getter
public class CommentAttitudeDto {
    private AttitudeDto attitudeDto;
    private Comment comment;

    public CommentAttitudeDto(Long lemonUserId, String name, Attitude attitude, Comment comment) {
        this.attitudeDto = new AttitudeDto(lemonUserId ,attitude,name);
        this.comment = comment;
    }

}
