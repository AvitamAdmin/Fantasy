package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DepositsLogRepository extends MongoRepository<DepositsLog, ObjectId> {
    Object findByStatus(String pending);

    Optional<DepositsLog> findById(String id);

    List<DepositsLog> findByDepositStatus(String approved);
}
