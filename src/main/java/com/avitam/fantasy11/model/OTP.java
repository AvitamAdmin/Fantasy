package com.avitam.fantasy11.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("OTP")
public class OTP extends CommonFields{
    private String userId;

    private String mobileOtp;

    private String emailOtp;
}
