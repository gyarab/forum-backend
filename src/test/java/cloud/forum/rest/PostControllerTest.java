package cloud.forum.rest;

import cloud.forum.domain.Forum;
import cloud.forum.domain.Post;
import cloud.forum.repository.ForumRepository;
import cloud.forum.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    public void createPost() throws Exception {

        Forum forum = new Forum();
        forum.setName("Bogus");
        forumRepository.saveAndFlush(forum);

        Post post = new Post();
        post.setForum(forum);
        mockMvc.perform(post("/post/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(post))
        ).andExpect(status().isCreated());
        Page<Post> posts = postRepository.findAllByForum(forum, PageRequest.of(0, 10));
        assertThat(posts).isNotEmpty();
    }
}