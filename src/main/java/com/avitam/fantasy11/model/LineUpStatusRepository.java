package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LineUpStatusRepository extends MongoRepository<LineUpStatus, ObjectId> {
    Optional<LineUpStatus> findById(String id);

    Optional<LineUpStatus> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<LineUpStatus> updateByRecordId(String recordId);
}
