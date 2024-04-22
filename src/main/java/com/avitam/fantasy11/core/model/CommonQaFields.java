package com.avitam.fantasy11.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@MappedSuperclass
public class CommonQaFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private String creator;
    @JsonIgnore
    private Boolean status;
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    @JsonIgnore
    private String modifier;
    private String identifier;
    private String shortDescription;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ParamInput> paramInput;
}
