package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchScoreForm extends BaseForm{

    private  int matchId;

    private int team1Score;

    private int team2Score;

    private float overs;
}
