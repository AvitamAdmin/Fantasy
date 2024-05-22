package com.avitam.fantasy11.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "node")
@Getter
@Setter
public class Node extends BaseEntity {

    private String path;

   private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    private Node parentNode;

    private ObjectId parentNodeId;

    private List<Node> childNodes;

    private Integer displayPriority = 1000;

}
