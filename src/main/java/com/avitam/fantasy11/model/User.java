package com.avitam.fantasy11.model;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Set;


@Document("User")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity{

    private String emailId;
    private String password;
    private String referredBy;
    private double balance;
    private double bonus;
    private String mobileNumber;
    private String dateOfBirth;
    private int role;
    private Binary profileImage;
    private String gender;
    private String language;
    private Set<Role>roles;
    @Transient
    private String passwordConfirm;
    private String ResetPasswordToken;

}
