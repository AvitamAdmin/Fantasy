package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContestJoinedRepository extends MongoRepository<ContestJoined, ObjectId> {
    Optional<ContestJoined> findById(String id);

    Optional<ContestJoined> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<ContestJoined> updateByRecordId(String recordId);
}