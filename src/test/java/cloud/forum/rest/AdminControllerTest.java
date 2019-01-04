package cloud.forum.rest;

import cloud.forum.domain.ForumUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("embedded-mariadb")
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Rollback(false)
    @WithMockUser
    public void createUser() throws Exception {
        ForumUser forumUser = new ForumUser("cool", "prase", true, Collections.singleton("USER"));
        mockMvc.perform(post("/admin/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsBytes(forumUser)))
                .andExpect(status().isCreated());
    }
}