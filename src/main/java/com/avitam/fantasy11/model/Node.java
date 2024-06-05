package com.avitam.fantasy11.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document("Node")
@Getter
@Setter
public class Node extends BaseEntity {

    private String name;

    private String path;

    private Set<Role> roles;

    private Node parentNode;

    private String parentNodeId;

    private List<Node> childNodes;

    private Integer displayPriority;

}
