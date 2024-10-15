package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("PointsUpdate")
public class PointsUpdate extends BaseEntity{

    private String matchId;

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
