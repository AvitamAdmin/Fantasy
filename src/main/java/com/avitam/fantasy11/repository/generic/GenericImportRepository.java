package com.avitam.fantasy11.repository.generic;


import com.avitam.fantasy11.model.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericImportRepository<T extends BaseEntity> extends MongoRepository<T, ObjectId> {
    BaseEntity findByRecordId(String recordId);

    BaseEntity findByIdentifier(String identifier);
}
