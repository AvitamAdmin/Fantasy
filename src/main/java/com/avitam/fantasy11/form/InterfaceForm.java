package com.avitam.fantasy11.form;

import com.avitam.fantasy11.core.model.Node;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InterfaceForm implements Serializable {
    private Long id;
    private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private String name;
    private String path;
    private Node parentNode;
    private String parentNodeId;
    private Integer displayPriority;
}
