package com.avitam.fantasy11.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document("VerificationToken")
public class VerificationToken {

    private int id;
    private int userId;
    private int token;
    private int status;
    public User user;
    private Date ExpiryDate;
}
