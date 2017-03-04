package com.jborn.gitlab.agiletools.service.gitlab;

import org.gitlab.api.GitlabAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

import javax.annotation.PostConstruct;

import static org.gitlab.api.TokenType.ACCESS_TOKEN;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

public abstract class GLAbstractService {
    @Autowired OAuth2ClientContext oauth2ClientContext;
    @Value("${gitlab.host}") String gitLabHost;

    @PostConstruct
    private void createGitLabApi() {
        this.api = GitlabAPI.connect(gitLabHost, oauth2ClientContext.getAccessToken().getValue(), ACCESS_TOKEN);
    }

    private GitlabAPI api;

    public GitlabAPI api() {
        return api;
    }
}
