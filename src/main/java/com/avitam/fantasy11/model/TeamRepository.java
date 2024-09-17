package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends MongoRepository<Team, ObjectId> {

    Optional<Team> findById(String id);

    Team findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Team> findByStatusOrderByIdentifier(Boolean b);
}
