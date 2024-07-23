package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContestRepository extends MongoRepository<Contest, ObjectId> {
    Optional<Contest> findById(String id);

    Optional<Contest> findByMainContestId(String id);
}