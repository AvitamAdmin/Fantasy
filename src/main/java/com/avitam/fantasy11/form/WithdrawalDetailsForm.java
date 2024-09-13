package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawalDetailsForm extends BaseForm{

    private int transaction;
    private String memberName;
    private String memberEmail;
    private double amountOfWithdraw;
    private double chargeOfWithdraw;
    private String withdrawMethod;
    private String processingTime;
    private String amountInMethodCurrency;
    private String dateOfCreate;
    private String details;
    private String withdrawalStatus;
    private String message;
}
