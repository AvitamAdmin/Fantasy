package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document("node")
@Getter
@Setter
public class Node extends BaseEntity {

    private String path;
    private String name;
    private Node parentNode;
    private ObjectId parentNodeId;
    private List<Node>childNodes;
    private Integer displayPriority;

}
