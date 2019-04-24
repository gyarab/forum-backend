package cloud.forum.rest;

import cloud.forum.domain.Forum;
import cloud.forum.repository.ForumRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ForumControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ForumRepository forumRepository;

    @Test
    @WithMockUser
    public void createForum() throws Exception {
        Forum forum = new Forum();
        forum.setName("Blah");
        String content = objectMapper.writeValueAsString(forum);
        mockMvc.perform(post("/forum/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
        ).andExpect(status().isCreated());
        List<Forum> forums = forumRepository.findForumsByNameContaining("Blah");
        assertThat(forums).isNotEmpty();
    }

}