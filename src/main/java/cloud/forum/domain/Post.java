package cloud.forum.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
//each Post is represented here through this class
@Entity
@Table(name="post")
@Getter
@Setter
public class Post {
    //mandatory id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //used later in the REST controller
    // to call out only the posts from a specific thread
    @Column(name="title")
    private String title;

    //the post itself
    @Column(name="content", length = 2048)
    private String content;

    //self-explanatory
    @Column(name="likes")
    private Long likes;

    //self-explanatory
    @Column(name="dislikes")
    private Long dislikes;

    //List<E> of comments, where each Comment is a class and a table,
    //containing things such as comment itself, likes, dislikes, etc.
    @JoinColumn(name="post_id")
    @OneToMany
    private List<Comment> comments;
}
