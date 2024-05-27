package com.avitam.fantasy11.model;

//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(collection = "node")
@Getter
@Setter
public class Node extends BaseEntity {

    //@Id
    //private ObjectId id;

    private int ids;

    private String path;

    private ObjectId parentNodeId;

    private List<String> childIds;

    private Integer displayPriority = 1000;

    private Node parentNode;

    private List<Node> childNodes;

    private Set<Role> roles;

    /*@ManyToMany(mappedBy = "permissions")
    private Set<User> user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Node parentNode;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentNode")
    private List<Node> childNodes;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;*/

}
