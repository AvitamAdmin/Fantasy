package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.BaseEntity;
import com.avitam.fantasy11.model.CommonFields;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommonDto {
    private String recordId;
    private String token;
    private String email;
    private String otp;
    private String mobileNumber;
    private String username;
    private ObjectId id;
    private String name;
    private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date creationTime;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date lastModified;
    private String modifiedBy;
    private String pic;
    private String identifier;
}

