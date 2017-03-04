package com.jborn.gitlab.agiletools.controllers;

import com.jborn.gitlab.agiletools.response.ProjectResponse;
import com.jborn.gitlab.agiletools.service.GitLabUserEventService;
import com.jborn.gitlab.agiletools.service.gitlab.GLProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.jborn.gitlab.agiletools.Constants.Projects.PROJECTS_LIST;
import static java.util.stream.Collectors.toList;

@RestController
public class ProjectController {
    @Autowired GLProjectService glProjectService;
    @Autowired GitLabUserEventService eventService;

    @RequestMapping(value = PROJECTS_LIST, method = RequestMethod.GET)
    public List<ProjectResponse> list() throws IOException {
        return glProjectService.list().stream().map(gitlabProject -> new ProjectResponse()
                .setProjectId(gitlabProject.getId())
                .setName(gitlabProject.getName())
                .setNamespace(gitlabProject.getNamespace().getName())
                .setUrl(gitlabProject.getWebUrl())
                .setHasSpentTime(eventService.projectHasEvent(gitlabProject.getId()))
        ).collect(toList());
    }
}
