package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.PendingWithdrawal;
import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.model.WithdrawalMethods;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WithdrawalDetailsDto extends CommonDto{

    private WithdrawalDetails withdrawalDetails;
    private List<WithdrawalDetails> withdrawalDetailsList;

    private PendingWithdrawal pendingWithdrawal;
    private List<PendingWithdrawal> pendingWithdrawalList;

    private WithdrawalMethods withdrawalMethods;
    private List<WithdrawalMethods> withdrawalMethodsList;
}
