package com.jborn.gitlab.agiletools.db.repository;

import java.util.List;

import com.jborn.gitlab.agiletools.db.model.GitLabUserEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GitLabUserEventRepository extends MongoRepository<GitLabUserEvent, String> {
    GitLabUserEvent findTopByProjectId(Integer projectId);
    List<GitLabUserEvent> findAllByProjectId(Integer projectId);
}
