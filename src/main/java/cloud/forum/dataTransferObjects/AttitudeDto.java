package cloud.forum.dataTransferObjects;

import cloud.forum.domain.enums.Attitude;
import lombok.Getter;

@Getter
class AttitudeDto {
    private Long lemonUserId;
    private Attitude attitude;
    AttitudeDto(Long lemonUserId, Attitude attitude) {
        this.lemonUserId = lemonUserId;
        this.attitude = attitude;
    }
}
