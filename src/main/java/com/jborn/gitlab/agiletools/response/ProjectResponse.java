package com.jborn.gitlab.agiletools.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProjectResponse {
    private Integer projectId;
    private String name;
    private String namespace;
    private String url;
    private boolean isHasSpentTime;
}
