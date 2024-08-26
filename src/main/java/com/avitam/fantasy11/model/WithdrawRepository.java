package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WithdrawRepository extends MongoRepository<Withdraw, ObjectId> {

    Optional<Withdraw> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<Withdraw> updateByRecordId(String recordId);
}
