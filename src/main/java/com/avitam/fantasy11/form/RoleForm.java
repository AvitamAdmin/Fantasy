package com.avitam.fantasy11.form;


import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoleForm implements Serializable {
    private Integer id;
    private String creator;
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private String name;
    private String roleId;
    private Boolean status;
    private Set<User> users;
    private Set<Node> permissions;
}
