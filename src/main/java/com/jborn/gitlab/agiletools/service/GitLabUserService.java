package com.jborn.gitlab.agiletools.service;

import com.jborn.gitlab.agiletools.db.model.User;
import com.jborn.gitlab.agiletools.db.repository.UserRepository;
import com.jborn.gitlab.agiletools.dto.UserDetails;
import com.jborn.gitlab.agiletools.dto.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
public class GitLabUserService implements UserService {
    @Autowired UserRepository userRepository;

    @Override
    public UserDetails currentUser() {
        return (UserDetails) getContext().getAuthentication().getPrincipal();
    }

    @Override
    public User getOrCreateUser(UserResult userResult) {
        Assert.notNull(userResult);

        User user = userRepository.findOne(userResult.getId());
        if (user == null) {
            user = userRepository.save(new User()
                    .setId(userResult.getId())
                    .setName(userResult.getName())
                    .setUsername(userResult.getUsername()));
        }

        return user;
    }
}
