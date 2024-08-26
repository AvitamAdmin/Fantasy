package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchTypeRepository extends MongoRepository<MatchType, ObjectId> {

    Optional<MatchType> findById(String id);

    Optional<MatchType> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<MatchType> updateByRecordId(String recordId);
}
