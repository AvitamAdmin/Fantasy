package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.api.ContestCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MatchesDto extends CommonDto {
    private String team1Id;
    private String team2Id;
    private String matchTime;
    private String eventStatus;
    private String tournamentId;
    private String sportTypeId;
    private String matchTypeId;
}
