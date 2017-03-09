package com.jborn.gitlab.agiletools.db.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jborn.gitlab.agiletools.db.model.enumeration.EventType;
import com.jborn.gitlab.agiletools.dto.MessageParseResult;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;

@Data
@Accessors(chain = true)
@Document
@Slf4j
public class GitLabUserEvent {
    @Id
    private String id;
    @DBRef
    private User user;
    private Integer projectId;
    private EventType eventType;
    private MessageParseResult logWork;
    private String raw;
    private String dateOfSpentTime;

    public ObjectNode convertRaw() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (ObjectNode) mapper.readTree(raw);
        } catch (IOException e) {
            log.error("Malformed json content: Can't parse '{}'", raw);
            return null;
        }
    }

    public Integer getSpentTime() {
        return logWork.getSpentTime();
    }

    public Integer getIssueId() {
        return logWork.getIssueId();
    }

    public User getUser() {
        return user;
    }

    public String getDateOfSpentTime() {
        return dateOfSpentTime;
    }
}
