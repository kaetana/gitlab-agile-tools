package com.jborn.gitlab.agiletools.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jborn.gitlab.agiletools.db.model.enumeration.EventType;
import com.jborn.gitlab.agiletools.dto.GitLabUserEventResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.jborn.gitlab.agiletools.db.model.enumeration.EventType.NOTE;

@Service
@Slf4j
public class GitLabUserEventParsingService implements ParsingService {
    private Map<EventType, ParsingService> map = new HashMap<>();

    public GitLabUserEventParsingService(
            @Autowired GitLabCommentParsingService commentService
    ) {
        map.put(NOTE, commentService);
    }

    @Override
    public GitLabUserEventResult parse(ObjectNode json) {
        EventType eventType = getGitLabUserEventTypeByJson(json);
        ParsingService parsingService = map.get(eventType);
        if (parsingService != null) {
            GitLabUserEventResult userEvent = parsingService.parse(json);

            if (userEvent != null) {
                return userEvent
                        .setEventType(eventType)
                        .setRaw(json.toString());
            }
        }

        return null;
    }

    private EventType getGitLabUserEventTypeByJson(ObjectNode json) {
        String type = json.get("object_kind").asText();
        try {
            return EventType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("'{}' entity type is not supported", type);
            return null;
        }
    }
}
