package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.UserTeam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserTeamsDto extends CommonDto {
    private String userId;
    private String teamName;
    private String teamId;
    private String contestId;
    private Set<UserTeam> players;
    private String team1Name;
    private String team2Name;
    private int team1Count;
    private int team2Count;
    private int wicketKeeperCount;
    private int bowlerCount;
    private int batsManCount;
    private int allRounderCount;

}
