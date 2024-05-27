package com.avitam.fantasy11.form;

import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class InterfaceForm implements Serializable {


    private String id;
    private int ids;
    private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private String name;
    private String path;
    private String modifier;
    private Node parentNode;
    private List<Node> childNodes;
    private Set<Role> roles;
  //  private String parentNodeId;
    //private ObjectId parentNodeId;
    private Integer displayPriority;
}
