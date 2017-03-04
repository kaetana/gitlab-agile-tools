package com.jborn.gitlab.agiletools.service;

import com.jborn.gitlab.agiletools.dto.MessageParseResult;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jborn.gitlab.agiletools.dto.MessageParsingResult.ERROR;
import static com.jborn.gitlab.agiletools.dto.MessageParsingResult.OK;
import static org.junit.Assert.assertEquals;

public class TimeLogServiceTest {
    TimeLogService subj = new TimeLogService();

    @Test
    public void parseStringWithOnlyIssueNumber() throws Exception {
        MessageParseResult result = subj.parseMessage("#31");

        assertEquals(new MessageParseResult(ERROR)
                .setIssueId(31), result);
    }

    @Test
    public void parseStringWithIssueNumberAndTime() throws Exception {
        MessageParseResult result = subj.parseMessage("#31 | :clock: 1h 34m");

        assertEquals(new MessageParseResult(OK)
                .setIssueId(31)
                .setSpentTime(94), result);
    }

    @Test
    public void parseStringWithIssueNumberAndComment() throws Exception {
        MessageParseResult result = subj.parseMessage("#31 | issue comment");

        assertEquals(new MessageParseResult(ERROR)
                .setIssueId(31)
                .setComment("issue comment"), result);
    }

    @Test
    public void parseFullMessage() throws Exception {
        MessageParseResult result = subj.parseMessage("#31 | :clock1: 1h 25m | issue comment");

        assertEquals(new MessageParseResult(OK)
                .setIssueId(31)
                .setSpentTime(85)
                .setComment("issue comment"), result);
    }

    private Map<String, MessageParseResult> cases = new HashMap<String, MessageParseResult>() {{
        put("#31|:clock1: 1h 25m|issue comment",
                new MessageParseResult(OK).setIssueId(31).setSpentTime(85).setComment("issue comment"));
        put("#34 |:clock: 25m|     issue clock1 comment",
                new MessageParseResult(OK).setIssueId(34).setSpentTime(25).setComment("issue clock1 comment"));
        put("#31        |:clock1:10h25m|issue comment 15h 24m",
                new MessageParseResult(OK).setIssueId(31).setSpentTime(625).setComment("issue comment 15h 24m"));
        put("#42|\n\n                      :clock1: 1h 25m   |\n\nissue comment",
                new MessageParseResult(OK).setIssueId(42).setSpentTime(85).setComment("issue comment"));
        put("#42\n|\n:clock1: 1h 25m\n|\nissue comment\n\n\n",
                new MessageParseResult(OK).setIssueId(42).setSpentTime(85).setComment("issue comment"));
        put("issue comment", new MessageParseResult(ERROR));
    }};
    @Test
    public void parseMessages() throws Exception {
        for (Map.Entry<String, MessageParseResult> entry : cases.entrySet()) {
            assertEquals(entry.getKey(), entry.getValue(), subj.parseMessage(entry.getKey()));
        }

    }
}