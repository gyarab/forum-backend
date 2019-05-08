package cloud.forum.domain;

import cloud.forum.domain.enums.Attitude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comment_attitude")
//Lombok's @Getter and @Setter automatically create getters and setters for all variables, so we don't need to write them.
@Getter
@Setter
public class CommentAttitude {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    CommentAttitude() {
    }

    public CommentAttitude(LemonUser user, Comment comment, Attitude attitude) {
        this.owner = user;
        this.comment = comment;
        this.attitude = attitude;
    }

    @Enumerated(EnumType.ORDINAL)
    private Attitude attitude;

    @ManyToOne()
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private LemonUser owner;
}
