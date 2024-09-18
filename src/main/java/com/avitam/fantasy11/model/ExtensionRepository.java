package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ExtensionRepository extends MongoRepository<Extension, ObjectId> {
    Extension findById(String id);

    Extension findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Extension> findByStatusOrderByIdentifier(Boolean b);


}
