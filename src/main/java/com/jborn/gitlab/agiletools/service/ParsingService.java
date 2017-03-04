package com.jborn.gitlab.agiletools.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jborn.gitlab.agiletools.dto.GitLabUserEventResult;

public interface ParsingService {
    GitLabUserEventResult parse(ObjectNode root);
}