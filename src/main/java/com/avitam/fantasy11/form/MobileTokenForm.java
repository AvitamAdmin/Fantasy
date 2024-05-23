package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileTokenForm extends BaseForm{

    private String userId;

    private String otp;
}
