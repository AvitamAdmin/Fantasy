package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
public class BaseForm {

    private String id;
    private String name;
    private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date creationTime;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date lastModified;
    private String modifier;
}
