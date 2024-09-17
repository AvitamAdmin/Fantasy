package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface
SportTypeRepository extends MongoRepository<SportType, ObjectId> {

    SportType findById(String id);

    SportType findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<SportType> findByStatusOrderByIdentifier(boolean b);
}
