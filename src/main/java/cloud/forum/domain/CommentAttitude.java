package cloud.forum.domain;

import cloud.forum.domain.enums.Attitude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comment_attitude")
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
