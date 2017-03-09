package com.jborn.gitlab.agiletools.dto;

import com.jborn.gitlab.agiletools.db.model.enumeration.EventType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GitLabUserEventResult {
    private Integer projectId;
    private UserResult user;
    private EventType eventType;
    private MessageParseResult logWork;
    private String raw;
    // what is better - Date or String? Date - is possible?
    private String dateOfSpentTime;
}
