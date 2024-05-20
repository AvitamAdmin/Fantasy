package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderBoard extends BaseForm {

    private int userId;
    private int tournamentId;
    private int matchesPlayed;
    private int rank;
    private  double bonusAmount;
}
