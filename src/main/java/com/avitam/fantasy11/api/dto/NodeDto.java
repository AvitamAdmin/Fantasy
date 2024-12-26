package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NodeDto extends CommonDto{

    private Node node;
    private String path;
    private Set<Role> roles;
    private String parentNode;
    private String parentNodeId;
    private List<Node> childNodes;
    private Integer displayPriority;
}
