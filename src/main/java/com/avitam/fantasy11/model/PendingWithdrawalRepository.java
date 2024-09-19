package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PendingWithdrawalRepository extends MongoRepository<PendingWithdrawal, ObjectId> {

    Optional<PendingWithdrawal> findById(String id);

    PendingWithdrawal findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<PendingWithdrawal> findByStatusOrderByIdentifier(Boolean b);
}
