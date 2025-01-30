package com.avitam.fantasy11.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Document("User")
@Getter
@Setter
@NoArgsConstructor
public class User extends CommonFields{

    private String password;
    private String referredBy;
    private double balance;
    private double bonus;
    private String mobileNumber;
    private String dateOfBirth;
    private Binary profileImage;
    private String gender;
    private String language;
    private Set<Role> roles;
    private String passwordConfirm;
    private String resetPasswordToken;
    private String emailOTP;
    private String mobileOTP;
    private String referralCode;

}
