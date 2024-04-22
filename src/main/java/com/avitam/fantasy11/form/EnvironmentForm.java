package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EnvironmentForm implements Serializable {
    private Long id;
    private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private String modifier;

    private String identifier;
    private String shortDescription;

    private String preConditionURL;
    private String hybrisUrlFormula;
    private String hybrisUrlExample;
    private String aemUrlFormula;
    private String aemUrlExample;
}

