package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WithdrawalMethodsRepository extends MongoRepository<WithdrawalMethods, ObjectId> {

    Optional<WithdrawalMethods> findById(String id);

    WithdrawalMethods findByMethodName(String methodName);

    WithdrawalMethods findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<WithdrawalMethods> findByStatusOrderByIdentifier(Boolean b);
}