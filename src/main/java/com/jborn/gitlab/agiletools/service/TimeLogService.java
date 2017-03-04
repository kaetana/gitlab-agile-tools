package com.jborn.gitlab.agiletools.service;

import com.jborn.gitlab.agiletools.dto.MessageParseResult;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jborn.gitlab.agiletools.dto.MessageParsingResult.ERROR;
import static com.jborn.gitlab.agiletools.dto.MessageParsingResult.OK;
import static java.util.regex.Pattern.compile;

@Service
public class TimeLogService {
    private static final Pattern issueNumberPattern = compile("^#(\\d*)((\\s*|)\\||$)");
    private static final Pattern timePattern = compile("\\|(\\s*|):clock(\\d*):(\\s*|)(.*?)(\\s*|)(\\||$)");
    private static final Pattern commentPattern = compile("\\|(\\s*|)(?!.*\\|)(?!.*:clock(\\d*):)(.+)$");

    public MessageParseResult parseMessage(String message) {
        message = message.trim();

        Matcher issueNumberMatcher = issueNumberPattern.matcher(message);
        Integer issueId;
        if (issueNumberMatcher.find()) {
            issueId = Integer.valueOf(issueNumberMatcher.group(1));
        } else return new MessageParseResult(ERROR);

        Matcher timeMatcher = timePattern.matcher(message);
        Integer spentTime = null;
        if (timeMatcher.find()) {
            spentTime = parseTime(timeMatcher.group(4));
        }

        Matcher commentMatcher = commentPattern.matcher(message);
        String comment = null;
        if (commentMatcher.find()) {
            comment = commentMatcher.group(3).trim();
        }

        return new MessageParseResult((spentTime == null)? ERROR : OK)
                .setIssueId(issueId)
                .setSpentTime(spentTime)
                .setComment(comment);
    }

    private static final Pattern hoursPattern = compile("(\\d*)(h|H)");
    private static final Pattern minutesPattern = compile("(\\d*)(m|M)");
    private Integer parseTime(String time) {
        Integer minutes = 0;
        Matcher hoursMatcher = hoursPattern.matcher(time);
        if (hoursMatcher.find()) {
            minutes += Integer.valueOf(hoursMatcher.group(1)) * 60;
        }

        Matcher minutesMatcher = minutesPattern.matcher(time);
        if (minutesMatcher.find()) {
            minutes += Integer.valueOf(minutesMatcher.group(1));
        }

        return minutes;
    }
}
