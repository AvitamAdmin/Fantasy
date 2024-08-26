package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SportsApiRepository extends MongoRepository<SportsApi, ObjectId> {

    Optional<SportsApi> findById(String id);

    Optional<SportsApi> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<SportsApi> updateByRecordId(String recordId);
}
