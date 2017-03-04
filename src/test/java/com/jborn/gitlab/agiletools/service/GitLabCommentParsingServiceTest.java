package com.jborn.gitlab.agiletools.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jborn.gitlab.agiletools.dto.GitLabUserEventResult;
import com.jborn.gitlab.agiletools.dto.MessageParseResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static com.jborn.gitlab.agiletools.dto.MessageParsingResult.OK;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GitLabCommentParsingServiceTest {
    @Autowired GitLabCommentParsingService parsingService;
    @Autowired ObjectMapper mapper;

    @MockBean TimeLogService timeLogService;

    @Value("classpath:comment-example.json") Resource commentExampleResource;

    ObjectNode objectNode;

    @Before
    public void setUp() throws Exception {
        URL commentExampleURL = commentExampleResource.getURL();

        objectNode = (ObjectNode) mapper.readTree(commentExampleURL);
    }

    @Test
    public void parseRootJsonObjectAndConstructGitLabUserEvent() throws Exception {
        String messageToParse = "#13 | :clock1: 1h 13m | Some comment";
        when(timeLogService.parseMessage(messageToParse))
                .thenReturn(new MessageParseResult(OK).setIssueId(13).setSpentTime(73).setComment("Some comment"));

        GitLabUserEventResult userEvent = parsingService.parse(objectNode);

        assertThat(userEvent.getLogWork().getResult(), is(OK));
        assertThat(userEvent.getLogWork().getSpentTime(), is(73));
        assertThat(userEvent.getUser().getUsername(), is("donsimon"));
        assertThat(userEvent.getProjectId(), is(20));
        verify(timeLogService).parseMessage(messageToParse);
        verifyNoMoreInteractions(timeLogService);
    }
}