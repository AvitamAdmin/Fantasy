package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SEORepository extends MongoRepository<SEO, ObjectId> {
    Optional<SEO> findById(String id);

    Optional<SEO> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


}
