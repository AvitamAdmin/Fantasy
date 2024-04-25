package com.avitam.fantasy11.form;

import com.avitam.fantasy11.model.Role;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
public class UserForm {

    private Long id;
    private String name;
    private String creator;
    //private Boolean status;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private String modifier;
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;
    private String referredBy;
    private double balance;
    private double bonus;
    private String mobileNumber;
    private String dateOfBirth;
    private int status;
    private int role;
    private Binary profileImage;
    private String gender;
    private String language;
    @ManyToMany
    private Set<Role> roles;
    private Locale locale;

}
