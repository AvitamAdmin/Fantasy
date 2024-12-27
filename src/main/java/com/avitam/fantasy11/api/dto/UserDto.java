package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto extends CommonDto{
    private String username;
    private String password;
    private String email;
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
    private String ResetPasswordToken;
    private String emailOTP;
    private String mobileOTP;
    private String referralCode;

}
