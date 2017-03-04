package com.jborn.gitlab.agiletools.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jborn.gitlab.agiletools.dto.GitLabUserEventResult;
import com.jborn.gitlab.agiletools.dto.MessageParseResult;
import com.jborn.gitlab.agiletools.dto.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitLabCommentParsingService implements ParsingService {
    @Autowired TimeLogService timeLogService;

    public GitLabUserEventResult parse(ObjectNode root) {
        String  gitLabComment = retrieveComment(root);
        MessageParseResult result = timeLogService.parseMessage(gitLabComment);
        if (result.isError()) {
            return null;
        }

        return new GitLabUserEventResult()
                .setUser(parseUser(root))
                .setProjectId(parseProjectId(root))
                .setLogWork(result);
    }

    private UserResult parseUser(ObjectNode objectNode) {
        ObjectNode user = (ObjectNode) objectNode.get("user");
        String name = user.get("name").asText();
        String username = user.get("username").asText();

        ObjectNode project = (ObjectNode) objectNode.get("object_attributes");
        String authorId = project.get("author_id").asText();

        return new UserResult()
                .setId(authorId)
                .setName(name)
                .setUsername(username);
    }

    private Integer parseProjectId(ObjectNode objectNode) {
        return objectNode.get("project_id").asInt();
    }

    private String retrieveComment(ObjectNode objectNode) {
        return objectNode.get("object_attributes").get("note").asText();
    }
}
