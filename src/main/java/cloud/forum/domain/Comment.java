package cloud.forum.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

//each Comment is represent here through this class
@Entity
@Table(name = "comment")
//Lombok's @Getter and @Setter automatically create getters and setters for all variables, so we don't need to write them.
@Getter
@Setter
public class Comment {
    //mandatory id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //content itself
    @Column(name = "content")
    private String content;
    //self-explanatory
    @Column(name = "likes")
    private Long likes;
    //self-explanatory
    @Column(name = "dislikes")
    private Long dislikes;
    // comments on comments => basically replies
    @JoinColumn(name = "replies_id")
    @OneToMany
    private List<Comment> comments;
    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "comment")
    private Set<CommentAttitude> attitudes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private LemonUser user;

}
