package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositsForm extends BaseForm{
    private String userId;
    private int payingAmount;
    private String gatewayName;
    private String transactionId;
    private String depositStatus;
}
