package com.jborn.gitlab.agiletools.db.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jborn.gitlab.agiletools.db.model.GitLabUserEvent;
import com.jborn.gitlab.agiletools.db.model.User;
import com.jborn.gitlab.agiletools.dto.MessageParseResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static com.jborn.gitlab.agiletools.db.model.enumeration.EventType.NOTE;
import static com.jborn.gitlab.agiletools.dto.MessageParsingResult.OK;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class GitLabUserEventRepositoryTest {
    GitLabUserEvent gitLabUserEvent;
    User user;
    String raw;

    @Autowired GitLabUserEventRepository subj;

    @Autowired UserRepository userRepository;
    @Value("classpath:comment-example.json") Resource commentExampleResource;

    @Before
    public void setUp() throws Exception {
        URL commentExampleURL = commentExampleResource.getURL();
        ObjectMapper mapper = new ObjectMapper();
        raw = mapper.readTree(commentExampleURL).asText();
        gitLabUserEvent = new GitLabUserEvent();

        user = new User()
                .setId("2")
                .setName("Sergey Simonov")
                .setUsername("donsimon");

        gitLabUserEvent
                .setUser(user)
                .setProjectId(1)
                .setEventType(NOTE)
                .setLogWork(new MessageParseResult(OK)
                        .setIssueId(13)
                        .setSpentTime(73)
                        .setComment("Some comment"))
                .setRaw(raw);

        userRepository.save(user);
        subj.save(gitLabUserEvent);
    }

    @Test
    public void saveEvent() throws Exception {
        String id = gitLabUserEvent.getId();
        GitLabUserEvent gitLabUserEventFromDB = subj.findOne(id);

        assertNotNull(gitLabUserEventFromDB.getId());
        assertThat(gitLabUserEventFromDB.getUser().getUsername(), is("donsimon"));
        assertThat(gitLabUserEventFromDB.getEventType(), is(NOTE));
    }

    @Test
    public void findTopByProjectId() throws Exception {
        assertNotNull(subj.findTopByProjectId(1));
    }
}