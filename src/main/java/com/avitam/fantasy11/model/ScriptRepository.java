package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScriptRepository extends MongoRepository<Script, ObjectId> {
}
