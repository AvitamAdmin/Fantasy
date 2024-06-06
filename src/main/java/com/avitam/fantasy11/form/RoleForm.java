package com.avitam.fantasy11.form;


import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoleForm extends BaseForm implements Serializable {

    private Set<User> users;
    private Set<Node> permissions;
}
