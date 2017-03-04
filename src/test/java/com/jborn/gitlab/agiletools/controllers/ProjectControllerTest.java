package com.jborn.gitlab.agiletools.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jborn.gitlab.agiletools.response.ProjectResponse;
import com.jborn.gitlab.agiletools.service.GitLabUserEventService;
import com.jborn.gitlab.agiletools.service.gitlab.GLProjectService;
import org.gitlab.api.models.GitlabNamespace;
import org.gitlab.api.models.GitlabProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static com.jborn.gitlab.agiletools.Constants.Projects.PROJECTS_LIST;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser
@WebMvcTest(ProjectController.class)
@ActiveProfiles("test")
public class ProjectControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean GLProjectService glProjectService;
    @MockBean GitLabUserEventService eventService;

    @Before
    public void setUp() throws IOException {
        GitlabProject gitlabProject = new GitlabProject();
        gitlabProject.setId(321);
        gitlabProject.setName("name");
        gitlabProject.setWebUrl("web-url");

        GitlabNamespace namespace = new GitlabNamespace();
        namespace.setName("namespace");
        gitlabProject.setNamespace(namespace);

        when(glProjectService.list()).thenReturn(singletonList(gitlabProject));
        when(eventService.projectHasEvent(321)).thenReturn(true);
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get(PROJECTS_LIST).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(singletonList(
                        new ProjectResponse()
                                .setName("name")
                                .setNamespace("namespace")
                                .setUrl("web-url")
                                .setProjectId(321)
                                .setHasSpentTime(true)
                ))));
    }
}