package com.jborn.gitlab.agiletools.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserDetails implements Serializable {
    private Long id;
    private String name;
    private String userName;
    private String email;
}
