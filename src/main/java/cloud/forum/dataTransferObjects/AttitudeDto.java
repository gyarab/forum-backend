package cloud.forum.dataTransferObjects;

import cloud.forum.domain.LemonUser;
import cloud.forum.domain.enums.Attitude;
import com.naturalprogrammer.spring.lemon.LemonService;
import lombok.Getter;

@Getter
class AttitudeDto {
    private String username;
    private Long lemonUserId;
    private Attitude attitude;
    AttitudeDto(Long lemonUserId, Attitude attitude,String name) {
        this.username = name;
        this.lemonUserId = lemonUserId;
        this.attitude = attitude;
    }
}
