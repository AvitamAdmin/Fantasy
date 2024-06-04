package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileTokenForm extends BaseForm{

    private Long mobileNumber;

    private String otp;
}
