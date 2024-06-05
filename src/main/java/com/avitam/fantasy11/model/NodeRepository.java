package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepository extends MongoRepository<Node, ObjectId> {

    Node getById(ObjectId aLong);

    List<Node> findByParentNodeId(String id);

    Optional<Node> findById(String id);

    Node findByParentNode(ObjectId parentNodeId);

    Node findByName(String admin);
}
