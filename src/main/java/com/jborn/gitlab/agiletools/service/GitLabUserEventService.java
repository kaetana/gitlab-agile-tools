package com.jborn.gitlab.agiletools.service;

import com.jborn.gitlab.agiletools.db.model.GitLabUserEvent;
import com.jborn.gitlab.agiletools.db.model.User;
import com.jborn.gitlab.agiletools.db.repository.GitLabUserEventRepository;
import com.jborn.gitlab.agiletools.dto.GitLabUserEventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitLabUserEventService {
    @Autowired UserService userService;
    @Autowired GitLabUserEventRepository eventRepository;

    public GitLabUserEvent save(GitLabUserEventResult userEvent) {
        User user = userService.getOrCreateUser(userEvent.getUser());

        return eventRepository.save(new GitLabUserEvent()
                .setUser(user)
                .setProjectId(userEvent.getProjectId())
                .setEventType(userEvent.getEventType())
                .setLogWork(userEvent.getLogWork())
                .setRaw(userEvent.getRaw()));
    }

    public boolean projectHasEvent(Integer projectId) {
        return eventRepository.findTopByProjectId(projectId) != null;
    }
}
