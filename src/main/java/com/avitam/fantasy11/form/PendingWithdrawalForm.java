package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PendingWithdrawalForm extends BaseForm{

    private String methodName;
    private String trx;
    private double amountOfWithdraw;
    private double amountInMethod;
    private String withdrawalStatus;


    /*private String currency;
    private double rate;
    private String processingTime;
    private double minimumAmount;
    private double maximumAmount;
    private double fixedCharge;
    private MultipartFile logo;*/
}
