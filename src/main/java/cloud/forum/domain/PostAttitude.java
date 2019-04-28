package cloud.forum.domain;

import cloud.forum.domain.enums.Attitude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "post_attitudes")
@Getter
@Setter
public class PostAttitude {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    PostAttitude(){
    }

    public PostAttitude(LemonUser user, Post post, Attitude attitude) {
        this.owner=user;
        this.post = post;
        this.attitude = attitude;

    }
    @Enumerated(EnumType.ORDINAL)
    private Attitude attitude;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private LemonUser owner;


}
