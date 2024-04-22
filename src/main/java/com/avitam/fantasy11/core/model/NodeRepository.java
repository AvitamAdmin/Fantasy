package com.avitam.fantasy11.core.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
    List<Node> findByParentNode(Node parentNode);

    Node findByPath(String path);

    Node findByName(String path);
}
