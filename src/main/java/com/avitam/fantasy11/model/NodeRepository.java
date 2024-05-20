package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepository extends MongoRepository<Node, ObjectId> {
    List<Node> findByParentNodeId(ObjectId  parentNodeId);
    Optional<Node> findById(ObjectId Id);

    Node getById(ObjectId id);

    //ObjectId getById(ObjectId id);
}
