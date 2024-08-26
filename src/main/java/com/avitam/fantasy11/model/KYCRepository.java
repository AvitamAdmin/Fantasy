package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KYCRepository extends MongoRepository<KYC, ObjectId> {

    Optional<KYC> findById(String objectId);

    Optional<KYC> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<KYC> updateByRecordId(String recordId);
}
