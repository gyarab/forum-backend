package cloud.forum.rest;

import cloud.forum.domain.Forum;
import cloud.forum.domain.Post;
import cloud.forum.repository.PostRepository;
import cloud.forum.service.ForumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commonsweb.util.LecwUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
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
    private ForumService forumService;
    @Autowired
    private PostRepository postRepository;

    @Test
    @WithMockUser(username = "admin")
    public void createPost() throws Exception {

        UserDto user = LecwUtils.currentUser();

        Forum forum = new Forum();
        forum.setName("Bogus");
        forum = forumService.createForum(forum, user);

        Post post = new Post();
        post.setForum(forum);
        mockMvc.perform(post("/post/create/{forumId}", forum.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(post))
        ).andExpect(status().isCreated());
        Page<Post> posts = postRepository.findAllByForum(forum, PageRequest.of(0, 10));
        assertThat(posts).isNotEmpty();
    }
}