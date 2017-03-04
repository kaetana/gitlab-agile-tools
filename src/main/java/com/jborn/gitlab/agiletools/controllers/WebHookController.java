package com.jborn.gitlab.agiletools.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jborn.gitlab.agiletools.dto.GitLabUserEventResult;
import com.jborn.gitlab.agiletools.service.GitLabUserEventParsingService;
import com.jborn.gitlab.agiletools.service.GitLabUserEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.jborn.gitlab.agiletools.Constants.WebHooksApi.WEB_HOOK;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Slf4j
public class WebHookController {
    @Autowired GitLabUserEventParsingService parsingService;
    @Autowired GitLabUserEventService eventService;

    @RequestMapping(value = WEB_HOOK, method = POST)
    public ResponseEntity hookUserEvent(@RequestBody ObjectNode json) {
        GitLabUserEventResult userEvent = parsingService.parse(json);
        if (userEvent != null) {
            eventService.save(userEvent);
        } else {
            log.warn("Received json {}", json.toString());
        }
        
        return ok().build();
    }
}
