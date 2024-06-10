package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Node> getAllNodes() {
        List<Node> allNodes = nodeRepository.findByParentNodeId(null);
        if (CollectionUtils.isNotEmpty(allNodes)) {
            allNodes.sort(Comparator.comparing(nodes -> nodes.getDisplayPriority(),
                    Comparator.nullsFirst(Comparator.naturalOrder())));

            for (Node node : allNodes) {
                List<Node> nodes1 = nodeRepository.findByParentNodeId(String.valueOf(node.getId()));
                for (Node childNode : nodes1) {
                    childNode.setParentNode(node); // Set parent node for each child node
                }
                node.setChildNodes(nodes1);
            }
        }
        return allNodes;
    }

    @Override
    public List<Node> getNodesForRoles() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User currentUser = userRepository.findByEmail(principalObject.getUsername());
        int roles = currentUser.getRole();
        Set<Node> nodes = new HashSet<>();

        if (currentUser.getRole() == 2) {
            nodes.addAll(nodeRepository.findByParentNodeId(null));
        }

        List<Node> allNodes = new ArrayList<>();
        List<Node> nodeList = nodes.stream().filter(node -> BooleanUtils.isTrue(node.getStatus())).collect(Collectors.toList());
        nodeList.sort(Comparator.comparing(node -> node.getDisplayPriority()));
        for (Node node : nodeList) {
            List<Node> nodes1 = nodeRepository.findByParentNodeId(String.valueOf(node.getId()));
            node.setChildNodes(nodes1);
            allNodes.add(node);
        }

        return allNodes;

    }
}