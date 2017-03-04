package com.jborn.gitlab.agiletools.service;

import com.jborn.gitlab.agiletools.db.model.GitLabUserEvent;
import com.jborn.gitlab.agiletools.db.model.User;
import com.jborn.gitlab.agiletools.db.repository.GitLabUserEventRepository;
import com.jborn.gitlab.agiletools.dto.GitLabUserEventResult;
import com.jborn.gitlab.agiletools.dto.MessageParseResult;
import com.jborn.gitlab.agiletools.dto.UserResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.jborn.gitlab.agiletools.db.model.enumeration.EventType.NOTE;
import static com.jborn.gitlab.agiletools.dto.MessageParsingResult.OK;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GitLabUserEventServiceTest {
    @InjectMocks GitLabUserEventService subj;

    @Mock UserService userService;
    @Mock GitLabUserEventRepository eventRepository;

    @Test
    public void saveEvent() throws Exception {
        GitLabUserEventResult result = new GitLabUserEventResult()
                .setUser(new UserResult().setId("1"))
                .setProjectId(1)
                .setEventType(NOTE)
                .setLogWork(new MessageParseResult(OK))
                .setRaw("123");

        User user = new User().setId("1");
        when(userService.getOrCreateUser(result.getUser())).thenReturn(user);

        GitLabUserEvent toSave = new GitLabUserEvent()
                .setUser(user)
                .setProjectId(1)
                .setLogWork(result.getLogWork())
                .setEventType(NOTE)
                .setRaw("123");
        GitLabUserEvent saved = new GitLabUserEvent()
                .setId("1")
                .setUser(user)
                .setProjectId(1)
                .setLogWork(result.getLogWork())
                .setEventType(NOTE)
                .setRaw("123");
        when(eventRepository.save(toSave)).thenReturn(saved);

        assertThat(subj.save(result), is(saved));

        verify(userService, times(1)).getOrCreateUser(result.getUser());
        verify(eventRepository, times(1)).save(toSave);
    }
}