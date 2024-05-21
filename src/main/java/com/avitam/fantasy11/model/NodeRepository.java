package com.avitam.fantasy11.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends MongoRepository<Node, Long> {
    List<Node> findByParentNode(Node parentNode);

    Node findByPath(String path);

    Node findByName(String path);

    Node getById(Long aLong);

    List<Node> findByParentNodeId(Object id);
}
