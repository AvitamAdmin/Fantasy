package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.model.Node;

import java.util.List;

public interface NodeService {
    //List<NodeDto> getAllNodes();
    List<Node> getAllNodes();

    //List<NodeDto> getNodesForRoles();
    List<Node> getNodesForRoles();
}
