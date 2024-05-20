package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWinningsForm extends BaseForm{

    private  int userId;

    private int matchId;

    private int teamId;

    private double winningAmount;
}
