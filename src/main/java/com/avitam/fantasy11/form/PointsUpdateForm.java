package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointsUpdateForm extends BaseForm {


    private int matchId;

    private int playerId;

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

    private float startingEleven;
}
