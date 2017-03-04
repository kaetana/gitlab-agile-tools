package com.jborn.gitlab.agiletools.service;

import com.jborn.gitlab.agiletools.db.model.User;
import com.jborn.gitlab.agiletools.db.repository.UserRepository;
import com.jborn.gitlab.agiletools.dto.UserResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GitLabUserServiceTest {
    @InjectMocks GitLabUserService subj;

    @Mock UserRepository userRepository;

    @Test
    public void getOrCreateUser_if_already_exists() throws Exception {
        User user = new User()
                .setId("1")
                .setName("name")
                .setUsername("username");
        when(userRepository.findOne("1")).thenReturn(user);

        assertThat(subj.getOrCreateUser(new UserResult().setId("1")), is(user));

        verify(userRepository, times(1)).findOne("1");
    }

    @Test
    public void getOrCreateUser_if_not_exists() throws Exception {
        User user = new User()
                .setId("1")
                .setName("name")
                .setUsername("username");
        when(userRepository.save(user)).thenReturn(user);

        UserResult userResult = new UserResult().setId("1").setName("name").setUsername("username");
        assertThat(subj.getOrCreateUser(userResult), is(user));

        verify(userRepository, times(1)).findOne("1");
        verify(userRepository, times(1)).save(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOrCreateUser_trying_null_save() throws Exception {
        subj.getOrCreateUser(null);
    }
}