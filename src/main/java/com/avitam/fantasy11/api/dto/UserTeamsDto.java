package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserTeamsDto extends CommonDto{

    private String userId;
    private String matchId;
    private Set<UserTeamDto> players;
    private String teamName;
    private String contestId;
    private String team1Name;
    private String team2Name;
    private int team1Count;
    private int team2Count;
    private int wicketKeeper;
    private int bowler;
    private int batsMan;
    private int allRounder;


}
