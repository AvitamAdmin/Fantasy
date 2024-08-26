package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LanguageRepository extends MongoRepository<Language, ObjectId> {

    Optional<Language> findById(String id);

    Optional<Language> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<Language> updateByRecordId(String recordId);
}
