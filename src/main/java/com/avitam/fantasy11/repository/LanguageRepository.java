package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Language;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("LanguageRepository")
public interface LanguageRepository extends GenericImportRepository<Language> {

    Optional<Language> findById(String id);

    Language findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Language> findByStatusOrderByIdentifier(boolean b);
}
