package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileTokenForm extends BaseEntityForm{

    private int userId;

    private String otp;
}
