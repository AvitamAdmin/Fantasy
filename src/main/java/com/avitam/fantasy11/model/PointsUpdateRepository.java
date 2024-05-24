package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointsUpdateRepository extends MongoRepository<PointsUpdate, ObjectId> {
    Optional<PointsUpdate> findById(String id);
}
