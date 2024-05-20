package com.avitam.fantasy11.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;


@Document("role")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {

    private String name;
    private String roleId;
    private Set<User> users;
    private Set<Node> permissions;
}
