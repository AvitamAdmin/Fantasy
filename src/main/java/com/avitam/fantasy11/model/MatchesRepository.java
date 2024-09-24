package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchesRepository extends MongoRepository<Matches, ObjectId> {
    Optional<Matches> findById(String id);

    List<Matches> findByEvent(String live);

    Matches findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Matches> findByStatusOrderByIdentifier(boolean b);

    List<Matches> findByStatusAndEventOrderByIdentifier(boolean b, String event);
}