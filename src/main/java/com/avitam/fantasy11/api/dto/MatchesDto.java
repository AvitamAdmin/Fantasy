package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Matches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MatchesDto extends CommonDto{

    private String team1Id;
    private String contestId;
    private String team2Id;
    private String dateAndTime;
    private String startDateAndTime;
    private String endDateAndTime;
    private String eventStatus;
    private boolean matchStatus;
    private String tournamentId;
    private String sportTypeId;
    private String mainContestId;
    private String matchTypeId;
    private String event;
    private List<String> teams;
    private List<String> contests;
    private List<String> matchTypeDtos;
    private List<String> sportTypeDtos;
    private List<String> tournamentDtos;

}
