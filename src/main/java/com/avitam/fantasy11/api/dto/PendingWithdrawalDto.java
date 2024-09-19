package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Address;
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

    private PendingWithdrawal pendingWithdrawal;
    private List<PendingWithdrawal> pendingWithdrawalList;
}
