package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Deposits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DepositsDto extends CommonDto {
    private String userId;
    private int payingAmount;
    private String gatewayName;
    private String transactionId;
    private String depositStatus;
    private List<Deposits> depositsList;
}
