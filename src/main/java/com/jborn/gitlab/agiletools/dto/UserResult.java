package com.jborn.gitlab.agiletools.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserResult {
    private String id;
    private String name;
    private String username;
}