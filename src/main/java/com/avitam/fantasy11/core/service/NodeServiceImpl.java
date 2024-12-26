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

    private static final String ADMIN_INTERFACE="/admin/interface";

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
    public List<Node> getAllNodes() {
        List<Node> allNodes = nodeRepository.findByParentNodeId(null);
        if (CollectionUtils.isNotEmpty(allNodes)) {
            allNodes.sort(Comparator.comparing(nodes -> nodes.getDisplayPriority(),
                    Comparator.nullsFirst(Comparator.naturalOrder())));

            for (Node node : allNodes) {
                List<Node> nodes1 = nodeRepository.findByParentNodeId(String.valueOf(node.getId()));
//                nodes1.sort(Comparator.comparing(nodes -> nodes.getDisplayPriority(),
//                        Comparator.nullsFirst(Comparator.naturalOrder())));
                for (Node childNode : nodes1) {
                    childNode.setParentNode(node.getName()); // Set parent node for each child node
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
        Set<Role> roles = currentUser.getRoles();
        Set<Node> nodes = new HashSet<>();
        for (Role role : roles) {
            nodes.addAll(role.getPermissions());
        }
        List<Node> allNodes = new ArrayList<>();
        List<Node> nodeList = nodes.stream().filter(node -> BooleanUtils.isNotFalse(node.getStatus())).collect(Collectors.toList());
        nodeList.sort(Comparator.comparing(node -> node.getDisplayPriority()));
        for (Node node : nodeList) {
            List<Node> nodes1 = nodeRepository.findByParentNodeId(String.valueOf(node.getId()));
            node.setChildNodes(nodes1);
            allNodes.add(node);
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
        Node node=null;
        List<Node> nodes = new ArrayList<>();
        List<NodeDto> nodeDtoList = nodeWsDto.getNodeDtoList();
        for(NodeDto nodeDto1 : nodeDtoList) {
            if (nodeDto1.getRecordId() != null) {
                node = nodeRepository.findByRecordId(nodeDto1.getRecordId());
                modelMapper.map(nodeDto1, node);
            } else {
                if (baseService.validateIdentifier(EntityConstants.NODE, nodeDto1.getIdentifier()) != null) {
                    nodeWsDto.setSuccess(false);
                    nodeWsDto.setMessage("Identifier already present");
                    return nodeWsDto;
                }
                node = modelMapper.map(nodeDto1, Node.class);
            }
           // baseService.populateCommonData(node);
           // node.setCreator(coreService.getCurrentUser().getCreator());
            nodeRepository.save(node);
            node.setLastModified(new Date());
            if (nodeWsDto.getRecordId() == null) {
                node.setRecordId(String.valueOf(node.getId().getTimestamp()));
            }
            nodeRepository.save(node);
            nodes.add(node);
            nodeWsDto.setMessage("Node Updated Successfully!");
            nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        }
        nodeWsDto.setNodeDtoList(modelMapper.map(nodes,List.class));
        return nodeWsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        Node node=nodeRepository.findByRecordId(recordId);
        if(node!=null) {

            nodeRepository.save(node);
        }
    }


}