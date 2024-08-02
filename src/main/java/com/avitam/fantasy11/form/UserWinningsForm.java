package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWinningsForm extends BaseForm{

    private String userId;

    private String matchId;

    private String userTeamId;

    private double winningAmount;
}
