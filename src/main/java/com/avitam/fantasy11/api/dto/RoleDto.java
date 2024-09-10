package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class RoleDto extends CommonDto{

    private Role role;
    private String baseUrl;
    private List<Role> roles;
}
