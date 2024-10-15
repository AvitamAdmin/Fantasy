package com.avitam.fantasy11.repository;

import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("NodeRepository")
public interface NodeRepository extends GenericImportRepository<Node> {


    List<Node> findByParentNodeId(String id);

    Optional<Node> findById(String id);

    Node findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Node> findByStatusOrderByIdentifier(boolean b);
}
