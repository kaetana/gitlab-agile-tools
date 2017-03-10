package com.jborn.gitlab.agiletools.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpentTimeResponse {
    private Integer issueId;
    private String issueLink;
    private String issueName;
    private String userName;
    // Date or String better?
    private String dateOfSpentTime;
    private Integer spentTime;
}
