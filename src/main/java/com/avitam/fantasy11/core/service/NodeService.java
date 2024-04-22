package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.core.dto.NodeDto;

import java.util.List;

public interface NodeService {
    List<NodeDto> getAllNodes();

    List<NodeDto> getNodesForRoles();
}
