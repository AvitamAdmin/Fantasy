package com.avitam.fantasy11.form;

import com.avitam.fantasy11.core.model.Node;
import com.avitam.fantasy11.core.model.UserTM;
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
    private Long id;
    private String creator;
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private String name;
    private Boolean status;
    private Set<UserTM> users;
    private Set<Node> permissions;
}
