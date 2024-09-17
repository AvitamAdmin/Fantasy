package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class WithdrawalMethodsForm extends BaseForm{

    private String methodName;
    private String currency;
    private double rate;
    private String processingTime;
    private double minimumAmount;
    private double maximumAmount;
    private double fixedCharge;
    private String withdrawalStatus;
    private MultipartFile logo;
}
