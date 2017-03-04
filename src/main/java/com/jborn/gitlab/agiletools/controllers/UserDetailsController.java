package com.jborn.gitlab.agiletools.controllers;

import com.jborn.gitlab.agiletools.dto.UserDetails;
import com.jborn.gitlab.agiletools.response.UserDetailsResponse;
import com.jborn.gitlab.agiletools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.jborn.gitlab.agiletools.Constants.UserApi.USER_DETAILS;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UserDetailsController {
    @Autowired UserService userService;

    @RequestMapping(value = USER_DETAILS, method = GET)
    public @ResponseBody UserDetailsResponse userDetails() {
        UserDetails currentUser = userService.currentUser();

        return new UserDetailsResponse()
                .setId(currentUser.getId())
                .setName(currentUser.getName())
                .setUsername(currentUser.getUserName());
    }
}
