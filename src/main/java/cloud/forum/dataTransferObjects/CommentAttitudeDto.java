package cloud.forum.dataTransferObjects;

import cloud.forum.domain.Comment;
import cloud.forum.domain.enums.Attitude;
import lombok.Getter;

@Getter
public class CommentAttitudeDto {
    private AttitudeDto attitudeDto;
    private Comment comment;

    public CommentAttitudeDto(Long lemonUserId, Attitude attitude, Comment comment) {
        this.attitudeDto = new AttitudeDto(lemonUserId,attitude);
        this.comment = comment;
    }

}
