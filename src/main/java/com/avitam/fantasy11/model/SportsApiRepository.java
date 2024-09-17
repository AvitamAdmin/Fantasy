package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SportsApiRepository extends MongoRepository<SportsApi, ObjectId> {

    Optional<SportsApi> findById(String id);

    SportsApi findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


    List<SportsApi> findByStatusOrderByIdentifier(boolean b);
}
