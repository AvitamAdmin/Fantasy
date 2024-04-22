package com.avitam.fantasy11.core.dto;

import com.avitam.fantasy11.core.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class NodeDto {
    private Long id;
    private String path;
    private String name;
    private Set<Role> roles;
    private NodeDto parentNode;
    private List<NodeDto> childNodes;
}
