package com.jborn.gitlab.agiletools.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import static com.jborn.gitlab.agiletools.dto.MessageParsingResult.ERROR;

@Data
@Accessors(chain = true)
public class MessageParseResult {
    private final MessageParsingResult result;

    private Integer issueId;
    private Integer spentTime;
    private String comment;

    public boolean isError() {
        return ERROR.equals(result);
    }
}
