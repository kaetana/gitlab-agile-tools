package com.jborn.gitlab.agiletools.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jborn.gitlab.agiletools.dto.GitLabUserEventResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.jborn.gitlab.agiletools.db.model.enumeration.EventType.NOTE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GitLabUserEventParsingServiceTest {
    @InjectMocks GitLabUserEventParsingService parsingService;

    @Mock GitLabCommentParsingService commentParsingService;

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        GitLabUserEventResult gitLabUserEvent = new GitLabUserEventResult();
        when(commentParsingService.parse(any())).thenReturn(gitLabUserEvent);
    }

    @Test
    public void parseCommentEvent() throws Exception {
        ObjectNode noteObjectNode = (ObjectNode) mapper.readTree("{\"object_kind\": \"note\"}");

        GitLabUserEventResult userEvent = parsingService.parse(noteObjectNode);

        assertNotNull(userEvent);
        assertThat(userEvent.getEventType(), is(NOTE));
        assertThat(userEvent.getRaw(), is("{\"object_kind\":\"note\"}"));
        verify(commentParsingService).parse(noteObjectNode);
        verifyNoMoreInteractions(commentParsingService);
    }

    @Test
    public void parsePushEvent() throws Exception {
        ObjectNode pushObjectNode = (ObjectNode) mapper.readTree("{\"object_kind\": \"push\"}");

        GitLabUserEventResult userEvent = parsingService.parse(pushObjectNode);

        assertNull(userEvent);
        verifyZeroInteractions(commentParsingService);
    }

    @Test
    public void parseFailed() throws Exception {
        ObjectNode wrongObjectNode = (ObjectNode) mapper.readTree("{\"object_kind\": \"catdogpig\"}");

        GitLabUserEventResult userEvent = parsingService.parse(wrongObjectNode);

        assertNull(userEvent);
        verifyZeroInteractions(commentParsingService);
    }
}