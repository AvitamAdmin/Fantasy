package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
public class BaseForm {

    private String id;
    private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private String modifier;
}