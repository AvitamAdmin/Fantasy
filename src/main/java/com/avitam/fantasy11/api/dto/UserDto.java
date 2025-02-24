package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto extends CommonDto {
    private String username;
    private String password;
    private String referredBy;
    private double balance;
    private double bonus;
    private String mobileNumber;
    private String dateOfBirth;
    private Binary profileImage;
    private String gender;
    private String language;
    private Set<String> roles;
    private String passwordConfirm;
    private String resetPasswordToken;
    private String emailOTP;
    private String mobileOTP;
    private String referralCode;
    private String token;
    private String email;
}
