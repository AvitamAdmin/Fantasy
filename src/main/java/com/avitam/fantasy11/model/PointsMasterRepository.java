package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointsMasterRepository extends MongoRepository<PointsMaster, ObjectId> {

    PointsMaster findById(String id);

    PointsMaster findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<PointsMaster> findByStatusOrderByIdentifier(Boolean b);
}
