package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class DepositsLog extends BaseEntity{
    private String userId;
    private int payingAmount;
    private String gatewayName;
    private String transactionId;
    private String depositStatus;
}