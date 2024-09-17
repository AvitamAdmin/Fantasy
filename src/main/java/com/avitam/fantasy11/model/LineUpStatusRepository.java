package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import  java.util.List;
@Repository
public interface LineUpStatusRepository extends MongoRepository<LineUpStatus, ObjectId> {

    LineUpStatus findById(String id);

    LineUpStatus findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<LineUpStatus> findByStatusOrderByIdentifier(Boolean b);


}
