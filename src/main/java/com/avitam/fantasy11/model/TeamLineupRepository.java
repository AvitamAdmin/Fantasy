package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamLineupRepository extends MongoRepository<TeamLineup, ObjectId> {

    Optional<TeamLineup> findById(String id);

    Optional<TeamLineup> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<TeamLineup> updateByRecordId(String recordId);
}
