package com.jborn.gitlab.agiletools.service;

import com.jborn.gitlab.agiletools.db.model.User;
import com.jborn.gitlab.agiletools.dto.UserDetails;
import com.jborn.gitlab.agiletools.dto.UserResult;

public interface UserService {
    UserDetails currentUser();

    User getOrCreateUser(UserResult userResult);
}
