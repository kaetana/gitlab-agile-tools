package com.jborn.gitlab.agiletools.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpentTimeDetails {
    private Integer issueId;
    private String userName;
    // Date or String better?
    private String dateOfSpentTime;
    private Integer spentTime;

    public Integer getIssueId() {
        return issueId;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateOfSpentTime() {
        return dateOfSpentTime;
    }

    public Integer getSpentTime() {
        return spentTime;
    }
}
