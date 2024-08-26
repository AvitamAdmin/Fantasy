package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TournamentRepository extends MongoRepository<Tournament, ObjectId> {

    Optional<Tournament> findById(String id);

    Optional<Tournament> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<Tournament> updateByRecordId(String recordId);
}
