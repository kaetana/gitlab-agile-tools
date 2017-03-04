package com.jborn.gitlab.agiletools.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jborn.gitlab.agiletools.db.model.GitLabUserEvent;
import com.jborn.gitlab.agiletools.dto.GitLabUserEventResult;
import com.jborn.gitlab.agiletools.service.GitLabUserEventParsingService;
import com.jborn.gitlab.agiletools.service.GitLabUserEventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.jborn.gitlab.agiletools.Constants.WebHooksApi.WEB_HOOK;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser
@WebMvcTest(value = WebHookController.class, secure = false)
@ActiveProfiles("test")
public class WebHookControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper mapper;

    @MockBean GitLabUserEventParsingService parsingService;
    @MockBean GitLabUserEventService service;
    @MockBean GitLabUserEventResult userEvent;
    @MockBean GitLabUserEvent userEventModel;

    @Value("classpath:comment-example.json") Resource commentExampleResource;

    @Test
    public void hookCommentEvent() throws Exception {
        ObjectNode objectNode = (ObjectNode) mapper.readTree(commentExampleResource.getURL());
        when(parsingService.parse(objectNode)).thenReturn(userEvent);
        when(service.save(userEvent)).thenReturn(userEventModel);

        mockMvc.perform(
                post(WEB_HOOK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectNode.toString())
        ).andExpect(status().isOk());

        verify(parsingService).parse(objectNode);
        verify(service).save(userEvent);
        verifyNoMoreInteractions(parsingService, service);
    }

    @Test
    public void DoNotHookWrongEvent() throws Exception {
        ObjectNode wrongObjectNode = (ObjectNode) mapper.readTree("{\"object_kind\": \"something wrong kind\"}");
        when(parsingService.parse(wrongObjectNode)).thenReturn(null);

        mockMvc.perform(
                post(WEB_HOOK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongObjectNode.toString())
        ).andExpect(status().isOk());

        verify(parsingService).parse(wrongObjectNode);
        verifyNoMoreInteractions(parsingService);
        verifyZeroInteractions(service);
    }
}