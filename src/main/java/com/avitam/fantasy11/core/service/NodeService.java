package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.api.dto.NodeDto;
import com.avitam.fantasy11.api.dto.NodeWsDto;
import com.avitam.fantasy11.model.Node;

import java.util.List;

public interface NodeService {
    List<Node> getAllNodes();

    List<Node> getNodesForRoles();

    Node findByRecordId(String recordId) ;

    void deleteByRecordId(String recordId) ;

    NodeWsDto handleEdit(NodeWsDto nodeWsDto);

    void updateByRecordId(String recordId);
}
