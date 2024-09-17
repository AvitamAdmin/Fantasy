package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Node;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NodeDto extends CommonDto{

    private Node node;
    private List<Node> nodeList;
}
