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
public class UserWinningsWsDto extends CommonWsDto{
    private List<UserWinningsDto> userWinningsDtoList;

    private String userId;

    private String matchId;

    private String userTeamId;

    private double winningAmount;
}
