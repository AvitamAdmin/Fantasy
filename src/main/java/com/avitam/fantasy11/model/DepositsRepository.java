package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DepositsRepository extends MongoRepository<Deposits, ObjectId> {

    Deposits findById(String id);

    List<Deposits> findByDepositStatus(String approved);

    Deposits findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Deposits> findByStatusOrderByIdentifier(boolean b);
}
