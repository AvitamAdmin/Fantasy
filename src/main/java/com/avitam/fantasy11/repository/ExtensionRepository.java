package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Extension;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("ExtensionRepository")
public interface ExtensionRepository extends GenericImportRepository<Extension> {
    Extension findById(String id);

    Extension findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Extension> findByStatusOrderByIdentifier(Boolean b);


}
