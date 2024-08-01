package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ExtensionRepository extends MongoRepository<Extension, ObjectId> {
    Optional<Extension> findById(String id);
}
