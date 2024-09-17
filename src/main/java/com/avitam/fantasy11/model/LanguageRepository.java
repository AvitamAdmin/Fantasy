package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends MongoRepository<Language, ObjectId> {

    Optional<Language> findById(String id);

    Language findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Language> findByStatusOrderByIdentifier(boolean b);
}
