package com.avitam.fantasy11.model;

import com.avitam.fantasy11.api.dto.UserTeamDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document("UserTeams")
public class UserTeams extends CommonFields {
    private String userId;
    private String matchId;
    private Set<UserTeam> players;
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
