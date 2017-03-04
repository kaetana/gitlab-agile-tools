package com.jborn.gitlab.agiletools.db.repository;

import com.jborn.gitlab.agiletools.db.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
