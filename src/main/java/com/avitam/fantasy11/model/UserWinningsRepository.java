package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserWinningsRepository extends MongoRepository<UserWinnings, ObjectId> {
    Optional<UserWinnings> findById(String id);

    UserWinnings findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<UserWinnings> findByStatusOrderByIdentifier(Boolean b);
}
