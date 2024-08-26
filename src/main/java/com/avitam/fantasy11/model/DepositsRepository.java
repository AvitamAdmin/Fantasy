package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DepositsRepository extends MongoRepository<Deposits, ObjectId> {
    Object findByStatus(String pending);

    Optional<Deposits> findById(String id);

    List<Deposits> findByDepositStatus(String approved);

    Optional<Deposits> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);


}
