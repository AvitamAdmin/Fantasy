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
    public List<Node>getAllNodes(){
        List<Node> nodesList=  nodeRepository.findByParentNodeId(null);
          if(CollectionUtils.isNotEmpty(nodesList)) {
              nodesList.sort(Comparator.comparing(nodes -> nodes.getDisplayPriority(),
                      Comparator.nullsFirst(Comparator.naturalOrder())));

              for (Node node : nodesList) {
                  List<Node> nodes1 = nodeRepository.findByParentNodeId(node.getId());
                  node.setChildNodes(nodes1);
              }
          }
     return nodesList;
    }

    /*@Override
    public List<String> getAllNodes() {
        List<Node> allNodes = new ArrayList<Node>();
        List<Node> nodesList = nodeRepository.findAll().stream().filter(node -> BooleanUtils.isTrue(node.getStatus()))
                .collect(Collectors.toList());

        nodesList.sort(Comparator.comparing(nodes -> nodes.getDisplayPriority(),
                Comparator.nullsFirst(Comparator.naturalOrder())));

        Map<String, List<Node>> parentToChildrenMap = new HashMap<>();

        // Populate parent to children map
        for (Node node : nodesList) {
            String parentId = node.getParentId();
            parentToChildrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(node);
        }

        // Recursively populate childIds for each parent node
        for (Node node : nodesList) {
            populateChildIds(node, parentToChildrenMap);
            allNodes.add(node);
        }
        return allNodes;
    }

    private void populateChildIds(Node parentNode, Map<String, List<Node>> parentToChildrenMap) {
        String parentId = String.valueOf(parentNode.getUid());
        List<Node> children = parentToChildrenMap.getOrDefault(parentId, Collections.emptyList());
        List<String> childIds = children.stream().map(childNode -> String.valueOf(childNode.getUid()))
                .collect(Collectors.toList());
        parentNode.setChildIds(childIds);
        for (Node child : children) {
            populateChildIds(child, parentToChildrenMap);
        }
    }*/


    @Override
    public List<Node> getNodesForRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User currentUser = userRepository.findByEmail(principalObject.getUsername());
        int roles = currentUser.getRole();
        Set<Node> nodes = new HashSet<>();

        List<Node> allNodes = new ArrayList<>();
        List<Node> nodeList = nodes.stream().filter(node -> BooleanUtils.isTrue(node.getStatus())).collect(Collectors.toList());
        nodeList.sort(Comparator.comparing(node -> node.getDisplayPriority()));
        for (Node node : nodeList) {
            if (CollectionUtils.isNotEmpty(node.getChildNodes())) {
               List<Node> childNodes = node.getChildNodes().stream().sorted(Comparator.comparing(nod -> nod.getDisplayPriority())).collect(Collectors.toList());
               node.setChildNodes(childNodes.stream().filter(childNode -> BooleanUtils.isTrue(childNode.equals(true))).collect(Collectors.toList()));
            }
            allNodes.add(node);
        }
        List<Node> treeNode = new ArrayList<>();
        for (Node node : allNodes) {
            if (node.getParentNodeId() == null) {
                if (!treeNode.stream().anyMatch(e -> e.getName().equals(node.getName()))) {
                    Node nod = modelMapper.map(node, Node.class);
                    nod.setChildNodes(new ArrayList<>());
                    treeNode.add(node);
                }
            } else {
                Node nodeDto=null;
                Node currentChild = modelMapper.map(node, Node.class);
                Node parentNodes = currentChild;
                List<Node> children = new ArrayList<>();
                Optional<Node> parentNode = treeNode.stream().filter(e -> e.getName().equals(parentNodes.getName())).findFirst();
                if (parentNode.isPresent()) {
                    children = parentNode.get().getChildNodes();
                    children.add (currentChild);
                    parentNode.get().setChildNodes(children);
                } else {
                    children.add(currentChild);
                    parentNodes.setChildNodes(children);
                    treeNode.add(parentNodes);
                }
            }
        }
        return treeNode;
    }
}

