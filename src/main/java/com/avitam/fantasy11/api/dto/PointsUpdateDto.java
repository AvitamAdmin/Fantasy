package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PointsUpdateDto extends CommonDto {

    private String matchId;

    private String tournamentId;

    private String playerId;

    private float battingPoints;

    private float bowlingPoints;

    private float fieldingPoints;

    private float totalPoints;

    private float economyPoints;

    private float maidenPoints;

    private float boundaryPoints;

    private float strikeRatePoints;

    private float centuryPoints;

    private float batsmanScore;

    private float bowlerWickets;

    private float catches;

    private float starting11;
}
