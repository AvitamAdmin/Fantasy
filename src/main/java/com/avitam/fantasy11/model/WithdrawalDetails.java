package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("WithdrawalDetails")
@Getter
@Setter
public class WithdrawalDetails extends CommonFields{

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
