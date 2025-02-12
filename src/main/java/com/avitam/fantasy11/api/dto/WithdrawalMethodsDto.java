package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WithdrawalMethodsDto extends CommonDto {

    private MultipartFile logo;
    private String methodName;
    private String currency;
    private double rate;
    private String processingTime;
    private double minimumAmount;
    private double maximumAmount;
    private double fixedCharge;
    private String withdrawalStatus;

}
