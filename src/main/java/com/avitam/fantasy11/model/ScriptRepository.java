package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ScriptRepository extends MongoRepository<Script, ObjectId> {

    Optional<Script> findById(String id);

    Optional<Script> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

}
