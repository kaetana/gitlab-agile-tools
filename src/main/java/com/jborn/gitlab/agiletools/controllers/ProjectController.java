package com.jborn.gitlab.agiletools.controllers;

import com.jborn.gitlab.agiletools.Constants;
import com.jborn.gitlab.agiletools.dto.SpentTimeDetails;
import com.jborn.gitlab.agiletools.response.ProjectResponse;
import com.jborn.gitlab.agiletools.response.SpentTimeResponse;
import com.jborn.gitlab.agiletools.service.GitLabUserEventService;
import com.jborn.gitlab.agiletools.service.gitlab.GLProjectService;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.jborn.gitlab.agiletools.Constants.Projects.PROJECT;
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

    @RequestMapping(value = PROJECT, method = RequestMethod.GET, params = {"projectId"})
    public List<SpentTimeResponse> listProjectSpentTime(Integer projectId) throws IOException {
        Iterable<SpentTimeDetails> spentTimeDetails = eventService.projectSpentTime(projectId);
        String issueUrl = getProjectUrl(projectId) + "issues/";
        return StreamSupport.stream(spentTimeDetails.spliterator(), false).map(details -> new SpentTimeResponse()
                .setIssueId(details.getIssueId())
                .setIssueLink(issueUrl + details.getIssueId())
                .setIssueName(getIssueName(projectId, details.getIssueId()))
                .setUserName(details.getUserName())
                .setDateOfSpentTime(details.getDateOfSpentTime())
                .setSpentTime(details.getSpentTime())).collect(Collectors.toList());
    }

    private String getIssueName(Integer projectId, Integer issueId) {
        try {
            return glProjectService.issueName(projectId,issueId);
        } catch (IOException e) {
            return Constants.NOT_AVAILABLE;
        }
    }

    private String getProjectUrl(Integer projectId) throws IOException {
        String projectUrl = glProjectService.project(projectId).getHttpUrl();
        if (!projectUrl.endsWith("/")) {
            projectUrl = projectUrl + "/";
        }
        return  projectUrl;
    }
}
