package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    Optional<Role> findById(String id);

    Role findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

}
