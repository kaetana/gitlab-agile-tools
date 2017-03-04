package com.jborn.gitlab.agiletools;

import com.jborn.gitlab.agiletools.dto.UserDetails;
import com.jborn.gitlab.agiletools.security.SessionUserInfoRestTemplateCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

import static com.jborn.gitlab.agiletools.Constants.WebHooksApi.WEB_HOOK;

@SpringBootApplication
public class GitlabAgileToolsApplication  {
	public static void main(String[] args) {
		SpringApplication.run(GitlabAgileToolsApplication.class, args);
	}

    @Configuration
    @Profile({"prod","dev"})
    @EnableWebSecurity
    @EnableMongoHttpSession
    @EnableOAuth2Sso
    static class OAuth2Config extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .logout().permitAll()
                    .and()
                    .authorizeRequests()
                        .antMatchers(WEB_HOOK + "**").permitAll()
                        .anyRequest().authenticated()
                    .and()
                    .csrf().disable();
        }

        @Bean
        public JdkMongoSessionConverter jdkMongoSessionConverter() {
            return new JdkMongoSessionConverter();
        }

        @Bean
        public PrincipalExtractor principalExtractor() {
            return map -> new UserDetails()
                    .setId(Long.valueOf((Integer) map.get("id")))
                    .setName((String) map.get("name"))
                    .setUserName((String) map.get("username"))
                    .setEmail((String) map.get("email"));
        }

        @Bean
        public UserInfoRestTemplateCustomizer userInfoRestTemplate() {
            return new SessionUserInfoRestTemplateCustomizer();
        }
    }
}