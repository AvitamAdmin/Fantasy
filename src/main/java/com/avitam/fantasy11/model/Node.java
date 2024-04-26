package com.avitam.fantasy11.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "node")
@Getter
@Setter
public class Node extends BaseEntity {

    @Column(name = "path")
    private String path;

   //@Column(name = "name")
   // private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Node parentNode;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentNode")
    private List<Node> childNodes;

    @Column(name = "displayPriority", columnDefinition = "Integer(10) default '1000'")
    private Integer displayPriority = 1000;

}
