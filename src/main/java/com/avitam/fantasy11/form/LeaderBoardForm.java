package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderBoardForm extends BaseForm{
    private String userId;

    private String tournamentId;

    private int matchesPlayed;

    private int rank;

    private  double bonusAmount;
}
