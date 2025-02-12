package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserWinningsDto extends CommonDto {
    private String userId;
    private String matchId;
    private String userTeamId;
    private double winningAmount;
    private Map<String, String> rankWinnings;
    private String contestId;
}
