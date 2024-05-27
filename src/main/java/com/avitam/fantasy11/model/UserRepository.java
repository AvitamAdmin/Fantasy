package com.avitam.fantasy11.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    User findByEmailId(String emailId);

    User findByResetPasswordToken(String token);

    User findByName(String username);
}
