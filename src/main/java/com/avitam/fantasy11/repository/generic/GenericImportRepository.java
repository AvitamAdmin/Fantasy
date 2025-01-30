package com.avitam.fantasy11.repository.generic;

import com.avitam.fantasy11.model.CommonFields;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericImportRepository<T extends CommonFields> extends MongoRepository<T, ObjectId> {

    CommonFields findByRecordId(String recordId);

    CommonFields findByIdentifier(String identifier);
}
