package com.jborn.gitlab.agiletools.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDetailsResponse {
    private Long id;
    private String username;
    private String name;
}
