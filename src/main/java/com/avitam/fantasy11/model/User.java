package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document("User")
@Getter
@Setter
@NoArgsConstructor
public class User {

    private String name;
    private String emailId;
    private String password;
    private String referredBy;
    private double balance;
    private double bonus;
    private String mobileNumber;
    private String dateOfBirth;
    private int status;
    private int role;
    private MultipartFile profileImage;
    private String gender;
    private String language;

}
