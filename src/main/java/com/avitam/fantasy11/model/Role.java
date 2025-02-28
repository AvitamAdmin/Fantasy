package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("Role")
@Getter
@Setter
@NoArgsConstructor
public class Role extends CommonFields {
    private String roleId;
    private Set<Node> permissions;
}
