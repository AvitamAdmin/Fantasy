package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.MobileToken;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SmsDto extends CommonDto{

    private String mobileNumber;

    private String otp;
}