package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class LeaderBoardDto extends CommonDto{

    private String userId;

    private String tournamentId;

    private int matchesPlayed;

    private int rank;

    private  double bonusAmount;
}
