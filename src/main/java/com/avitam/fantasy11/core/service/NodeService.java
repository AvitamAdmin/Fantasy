package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.model.Node;

import java.util.List;

public interface NodeService {
    List<Node> getAllNodes();

    List<Node> getNodesForRoles();
}
