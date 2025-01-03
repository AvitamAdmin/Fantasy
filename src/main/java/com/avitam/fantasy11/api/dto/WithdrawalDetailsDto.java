package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WithdrawalDetailsDto extends CommonDto{

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
