package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document("node")
@Getter
@Setter
public class Node extends CommonFields {

    private String path;
    private Set<Role> roles;
    private Node parentNode;
    private String parentNodeId;
    private List<Node> childNodes;
    private Integer displayPriority;

}
