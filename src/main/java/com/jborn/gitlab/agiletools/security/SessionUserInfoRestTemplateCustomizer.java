package com.jborn.gitlab.agiletools.security;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

public class SessionUserInfoRestTemplateCustomizer implements UserInfoRestTemplateCustomizer {
    @Override
    public void customize(OAuth2RestTemplate template) {
        template.setAuthenticator((oAuth2ProtectedResourceDetails, oAuth2ClientContext, clientHttpRequest) -> {
            clientHttpRequest.getHeaders().add("Authorization", "Bearer " + oAuth2ClientContext.getAccessToken());
        });
    }
}
