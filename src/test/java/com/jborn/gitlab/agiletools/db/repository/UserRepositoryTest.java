package com.jborn.gitlab.agiletools.db.repository;

import com.jborn.gitlab.agiletools.db.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class UserRepositoryTest {
    User user;
    @Autowired UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        user = new User()
                .setId("2")
                .setName("Sergey Simonov")
                .setUsername("donsimon");
    }

    @Test
    public void saveUser() throws Exception {
        userRepository.save(user);
        User actualUser = userRepository.findOne("2");

        assertThat(actualUser.getId(), is("2"));
    }
}