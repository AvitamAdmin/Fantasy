package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GatewaysManualRepository extends MongoRepository<GatewaysManual, ObjectId> {

    Optional<GatewaysManual> findById(String id);

    GatewaysManual findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<GatewaysManual> findByStatusOrderByIdentifier(Boolean b);
}
