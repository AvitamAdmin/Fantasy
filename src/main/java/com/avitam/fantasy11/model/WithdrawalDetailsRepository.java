package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WithdrawalDetailsRepository extends MongoRepository<WithdrawalDetails, ObjectId> {

    Optional<WithdrawalDetails> findById(String id);

    WithdrawalDetails findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<WithdrawalDetails> findByStatusOrderByIdentifier(Boolean b);
}
