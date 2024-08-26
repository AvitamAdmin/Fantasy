package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchScoreRepository extends MongoRepository<MatchScore, ObjectId> {

    Optional<MatchScore> findById(String id);

    Optional<MatchScore> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<MatchScore> updateByRecordId(String recordId);
}
