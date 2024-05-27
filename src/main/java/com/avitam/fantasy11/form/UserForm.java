package com.avitam.fantasy11.form;

import com.avitam.fantasy11.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserForm {
    private int ids;
    private String name;
    private String emailId;
    private String password;
    private String passwordConfirm;
    private String referredBy;
    private double balance;
    private double bonus;
    private String mobileNumber;
    private String dateOfBirth;
    private boolean status;
    private int role;
    private Binary profileImage;
    private String gender;
    private String language;
    private String creator;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModified;
    private String modifier;
    private Set<Role> roles;
    private String email;
}
