package com.avitam.fantasy11.form;

import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoleForm extends BaseForm implements Serializable {

    private String roleId;
    private  int ids;
    private Set<User> users;
    private Set<Node> permissions;
}
