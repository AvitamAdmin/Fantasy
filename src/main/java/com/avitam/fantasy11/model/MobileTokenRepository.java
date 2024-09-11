package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MobileTokenRepository extends MongoRepository<MobileToken, ObjectId> {
    Optional<MobileToken> findById(String id);

    MobileToken findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<MobileToken> findStatusOrderByIdentifier(boolean b);
}