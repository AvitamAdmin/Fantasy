package com.avitam.fantasy11.form;

import com.avitam.fantasy11.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserForm extends BaseForm{

    private String email;
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
    private List<Role> roles;

}
