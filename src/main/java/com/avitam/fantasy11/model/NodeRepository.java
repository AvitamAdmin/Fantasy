package com.avitam.fantasy11.model;

import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepository extends MongoRepository<Node, ObjectId> {
    List<Node> findByParentNode(Node parentNode);

    Node findByPath(String path);

    Node findByName(String path);

    //Node getByIds(Long aLong);
    Node getById(ObjectId aLong);

    //Node findById(ObjectId id);

    //Node deleteById(ObjectId parentNodeId);

    List<Node> findByParentNodeId(ObjectId parentNodeId);

    Optional<Node> findById(String id);
}
