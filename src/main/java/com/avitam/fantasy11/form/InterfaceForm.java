package com.avitam.fantasy11.form;

import com.avitam.fantasy11.model.Node;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InterfaceForm extends BaseForm implements Serializable {

    private String name;
    private String path;
    private Node parentNode;
   // private String parentNodeId;
    private Integer displayPriority;
}
