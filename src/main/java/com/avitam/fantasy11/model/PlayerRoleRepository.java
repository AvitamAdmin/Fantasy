package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRoleRepository extends MongoRepository<PlayerRole, ObjectId> {
    Optional<PlayerRole> findById(String id);

    Optional<PlayerRole> findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    Optional<PlayerRole> updateByRecordId(String recordId);
}
