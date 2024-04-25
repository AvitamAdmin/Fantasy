package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.core.dto.NodeDto;
import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.NodeRepository;
import com.avitam.fantasy11.model.Role;
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
    private UserTMRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    //@Cacheable(cacheNames = "allNodes")
    public List<NodeDto> getAllNodes() {
        List<NodeDto> allNodes = new ArrayList<>();
        List<Node> nodeList = nodeRepository.findAll().stream().filter(node -> BooleanUtils.isTrue(node.getStatus())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(nodeList)) {
            nodeList.sort(Comparator.comparing(nodes -> nodes.getDisplayPriority(),
                    Comparator.nullsLast(Comparator.naturalOrder())));
            for (Node node : nodeList) {
                if (CollectionUtils.isNotEmpty(node.getChildNodes())) {
                    List<Node> childNodes = node.getChildNodes().stream().sorted(Comparator.comparing(nodes -> nodes.getDisplayPriority())).collect(Collectors.toList());
                    node.setChildNodes(childNodes.stream().filter(childNode -> BooleanUtils.isTrue(childNode.getStatus())).collect(Collectors.toList()));
                }
                allNodes.add(modelMapper.map(node, NodeDto.class));
            }
        }
        return allNodes;
    }

    @Override
    //@Cacheable(cacheNames = "roleBasedNodes")
    public List<NodeDto> getNodesForRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        UserTM currentUser = userRepository.findByUsername(principalObject.getUsername());
        Set<Role> roles = currentUser.getRoles();
        Set<Node> nodes = new HashSet<>();
        for (Role role : roles) {
            nodes.addAll(role.getPermissions());
        }
        List<Node> allNodes = new ArrayList<>();
        List<Node> nodeList = nodes.stream().filter(node -> BooleanUtils.isTrue(node.getStatus())).collect(Collectors.toList());
        nodeList.sort(Comparator.comparing(node -> node.getDisplayPriority()));
        for (Node node : nodeList) {
            if (CollectionUtils.isNotEmpty(node.getChildNodes())) {
                List<Node> childNodes = node.getChildNodes().stream().sorted(Comparator.comparing(node1 -> node1.getDisplayPriority())).collect(Collectors.toList());
                node.setChildNodes(childNodes.stream().filter(childNode -> BooleanUtils.isTrue(childNode.getStatus())).collect(Collectors.toList()));
            }
            allNodes.add(node);
        }
        List<NodeDto> treeNode = new ArrayList<>();
        for (Node node : allNodes) {
            if (node.getParentNode() == null) {
                if (!treeNode.stream().filter(e -> e.getName().equals(node.getName())).findFirst().isPresent()) {
                    NodeDto nodeDto = modelMapper.map(node, NodeDto.class);
                    nodeDto.setChildNodes(new ArrayList<>());
                    treeNode.add(nodeDto);
                }
            } else {
                NodeDto currentChildDto = modelMapper.map(node, NodeDto.class);
                NodeDto parentNodeDto = currentChildDto.getParentNode();
                List<NodeDto> children = new ArrayList<>();
                Optional<NodeDto> parentNode = treeNode.stream().filter(e -> e.getName().equals(parentNodeDto.getName())).findFirst();
                if (parentNode.isPresent()) {
                    children = parentNode.get().getChildNodes();
                    children.add(currentChildDto);
                    parentNode.get().setChildNodes(children);
                } else {
                    children.add(currentChildDto);
                    parentNodeDto.setChildNodes(children);
                    treeNode.add(parentNodeDto);
                }
            }
        }
        return treeNode;
    }
}
