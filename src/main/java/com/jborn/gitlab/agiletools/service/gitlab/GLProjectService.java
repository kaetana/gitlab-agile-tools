package com.jborn.gitlab.agiletools.service.gitlab;

import org.gitlab.api.models.GitlabProject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Service
@Scope(value = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
public class GLProjectService extends GLAbstractService {
    public List<GitlabProject> list() throws IOException {
        return api().getProjects();
    }
}
