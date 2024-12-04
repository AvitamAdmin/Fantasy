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

public class RoleDto extends CommonDto{

    private String roleId;
    private Set<Node> permissions;
}
