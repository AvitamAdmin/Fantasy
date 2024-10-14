package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String email);

    User findByResetPasswordToken(String token);

    User findByEmail(String email);

    User findById(String id);

    User findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


    User findByMobileNumber(String mobileNumber);

    List<User> findByStatusOrderByIdentifier(boolean b);
}
