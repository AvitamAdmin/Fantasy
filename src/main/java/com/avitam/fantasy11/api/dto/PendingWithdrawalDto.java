package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PendingWithdrawalDto extends CommonDto {

    private String methodName;
    private String trx;
    private double amountOfWithdraw;
    private double amountInMethod;
    private String withdrawalStatus;
}
