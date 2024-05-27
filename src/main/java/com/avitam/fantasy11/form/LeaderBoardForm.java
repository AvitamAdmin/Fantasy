package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderBoardForm extends BaseEntityForm{
    private int userId;

    private int tournamentId;

    private int matchesPlayed;

    private int rank;

    private  double bonusAmount;
}
