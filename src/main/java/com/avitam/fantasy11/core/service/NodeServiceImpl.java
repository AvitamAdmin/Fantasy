package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.api.dto.NodeDto;
import com.avitam.fantasy11.api.dto.NodeWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.NodeRepository;
import com.avitam.fantasy11.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;
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

    private static final String ADMIN_INTERFACE = "/admin/interface";

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    @Override
    public List<NodeDto> getAllNodes() {
        List<NodeDto> allNodes = new ArrayList<>();
        List<Node> nodeList = nodeRepository.findByParentNode(null);
        if (CollectionUtils.isNotEmpty(nodeList)) {
            for (Node node : nodeList) {
                NodeDto nodeDto = new NodeDto();
                modelMapper.map(node, nodeDto);
                List<Node> childNodes = nodeRepository.findByParentNodeId(node.getRecordId());
                if (CollectionUtils.isNotEmpty(childNodes)) {
                    List<Node> childNodeList = childNodes.stream().filter(childNode -> BooleanUtils.isTrue(childNode.getStatus()))
                            .sorted(Comparator.comparing(nodes -> nodes.getDisplayPriority())).collect(Collectors.toList());
                    nodeDto.setChildNodes(modelMapper.map(childNodeList, List.class));
                }
                allNodes.add(nodeDto);
            }
        }
        return allNodes;
    }

    @Override
    //@Cacheable(cacheNames = "roleBasedNodes")
    public List<NodeDto> getNodesForRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User currentUser = userRepository.findByUsername(principalObject.getUsername());
        Set<Role> roles = currentUser.getRoles();
        Set<Node> nodes = new HashSet<>();
        for (Role role : roles) {
            nodes.addAll(role.getPermissions());
        }
        List<NodeDto> allNodes = new ArrayList<>();
        Set<Node> nodeList = nodes.stream().filter(node -> BooleanUtils.isTrue(node.getStatus())).collect(Collectors.toSet());
        List<Node> nodeListArray = new ArrayList<>(nodeList);
        nodeListArray.sort(Comparator.comparing(node -> node.getDisplayPriority(),
                Comparator.nullsLast(Comparator.naturalOrder())));
        Map<String, Set<Node>> parentChildNodes = new HashMap<>();
        for (Node node : nodeListArray) {
            String parent = node.getParentNode().getIdentifier();
            Set<Node> childNodes = new HashSet<>();
            if (parentChildNodes.containsKey(parent)) {
                childNodes.addAll(parentChildNodes.get(parent));
            }
            childNodes.add(node);
            parentChildNodes.put(parent, childNodes);
        }

        for (String key : parentChildNodes.keySet()) {
            NodeDto nodeDto = new NodeDto();
            nodeDto.setIdentifier(key);
            nodeDto.setChildNodes(modelMapper.map(parentChildNodes.get(key), List.class));
            allNodes.add(nodeDto);
        }
        return allNodes;
    }

    @Override
    public Node findByRecordId(String recordId) {

        return nodeRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        nodeRepository.deleteByRecordId(recordId);
    }

    @Override
    public NodeWsDto handleEdit(NodeWsDto nodeWsDto) {
        Node node = null;
        List<Node> nodes = new ArrayList<>();
        List<NodeDto> nodeDtoList = nodeWsDto.getNodeDtoList();
        for (NodeDto nodeDto1 : nodeDtoList) {
            if (nodeDto1.getRecordId() != null) {
                node = nodeRepository.findByRecordId(nodeDto1.getRecordId());
                modelMapper.map(nodeDto1, node);
                nodeRepository.save(node);
                nodeWsDto.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.NODE, nodeDto1.getIdentifier()) != null) {
                    nodeWsDto.setSuccess(false);
                    nodeWsDto.setMessage("Identifier already present");
                    return nodeWsDto;
                }
                node = modelMapper.map(nodeDto1, Node.class);
            }
            baseService.populateCommonData(node);
            nodeRepository.save(node);
            if (node.getRecordId() == null) {
                node.setRecordId(String.valueOf(node.getId().getTimestamp()));
            }
            nodeRepository.save(node);
            nodes.add(node);
            nodeWsDto.setMessage("Node added Successfully!");
            nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        }
        nodeWsDto.setNodeDtoList(modelMapper.map(node, List.class));
        return nodeWsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        Node node = nodeRepository.findByRecordId(recordId);
        if (node != null) {

            nodeRepository.save(node);
        }
    }


}