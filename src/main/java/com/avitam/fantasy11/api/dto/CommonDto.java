package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommonDto implements Serializable {
    private String recordId;
    private ObjectId id;
    private String name;
    private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date creationTime;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date lastModified;
    private String modifiedBy;
    private String identifier;
}

