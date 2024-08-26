package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ExtensionRepository extends MongoRepository<Extension, ObjectId> {
    Optional<Extension> findById(String id);

    Optional<Extension> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


}
