package com.avitam.fantasy11.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PendingWithdrawalWsDto extends CommonWsDto{
 private List<PendingWithdrawalDto> pendingWithdrawalDtoList;
}
