package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.WithdrawalMethods;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WithdrawalMethodsDto extends CommonDto{

//    private WithdrawalMethods withdrawalMethods;
//    private List<WithdrawalMethods> WithdrawalMethodsList;
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
