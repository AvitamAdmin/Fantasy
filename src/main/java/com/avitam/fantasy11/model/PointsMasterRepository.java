package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointsMasterRepository extends MongoRepository<PointsMaster, ObjectId> {

    Optional<PointsMaster> findById(String id);

    Optional<PointsMaster> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<PointsMaster> updateByRecordId(String recordId);
}
