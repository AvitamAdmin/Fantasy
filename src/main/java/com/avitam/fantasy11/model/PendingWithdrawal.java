package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("PendingWithdrawal")
public class PendingWithdrawal extends BaseEntity {

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
    private Binary logo;*/

}
