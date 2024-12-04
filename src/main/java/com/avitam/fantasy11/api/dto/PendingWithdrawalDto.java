package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.PendingWithdrawal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PendingWithdrawalDto extends CommonDto{

    private String methodName;
    private String trx;
    private double amountOfWithdraw;
    private double amountInMethod;
    private String withdrawalStatus;
}
