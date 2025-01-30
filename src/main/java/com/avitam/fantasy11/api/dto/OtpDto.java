package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@NoArgsConstructor
@ToString
public class OtpDto extends CommonDto{
    private String userId;

    private String mobileOtp;

    private String emailOtp;
}
