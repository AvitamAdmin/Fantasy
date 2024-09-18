package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.api.dto.NodeDto;
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
    @Autowired
    private CoreService coreService;

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
    public NodeDto handleEdit(NodeDto request) {
        NodeDto nodeDto=new NodeDto();
        Node node=null;
        if (request.getRecordId()!=null){
            Node requestData=request.getNode();
            node=nodeRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,node);
        }else {
            node=request.getNode();
            node.setCreator(coreService.getCurrentUser().getUsername());
            node.setCreationTime(new Date());
            nodeRepository.save(node);
        }
        node.setLastModified(new Date());
        if (request.getRecordId()==null){
            node.setRecordId(String.valueOf(node.getId().getTimestamp()));
        }
        nodeRepository.save(node);
        nodeDto.setNode(node);
        return nodeDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        Node node=nodeRepository.findByRecordId(recordId);
        if(node!=null) {

            nodeRepository.save(node);
        }
    }


}