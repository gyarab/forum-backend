package cloud.forum.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

//Main table
@Entity
@Table(name = "forum")
/**
 * lombok creates get and set methods for us
 */
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Forum {
    //mandatory id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // this column is a List<E> of posts,
    // where each post is a class and a table containing
    // things such as the content itself, likes, dislikes and comments
    @OneToMany
    @JoinColumn(name = "forum_id")
//    @JsonBackReference("posts")
    private List<Post> posts;

    @Column(length = 128)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
//    @JsonManagedReference("sub_forums")
    private Forum parent;

    //Collect all Forum classes to which this class is their parent.
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
//    @JsonBackReference("sub_forums")
    private List<Forum> children;
}
