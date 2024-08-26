package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralRepository extends MongoRepository<General, ObjectId> {
    Optional<General> findById(String id);

    Optional<General> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


}
