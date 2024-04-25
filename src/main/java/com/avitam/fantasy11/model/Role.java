package com.avitam.fantasy11.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {

    private String roleId;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    @ManyToMany
    private Set<Node> permissions;
}
