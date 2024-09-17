package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("WithdrawalMethods")
public class WithdrawalMethods extends BaseEntity {

    private String methodName;
    private String currency;
    private double rate;
    private String processingTime;
    private double minimumAmount;
    private double maximumAmount;
    private double fixedCharge;
    private String withdrawalStatus;
    private Binary logo;

}
