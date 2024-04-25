package com.avitam.fantasy11.form;

import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.User;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class RoleForm {

    private Long id;
    private String name;
    private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private String modifier;
    private String roleId;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    @ManyToMany
    private Set<Node> permissions;
}
