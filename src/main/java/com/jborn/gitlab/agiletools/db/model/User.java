package com.jborn.gitlab.agiletools.db.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document
public class User {
    @Id
    private String id;
    private String name;
    private String username;

    public String getName() {
        return name;
    }
}