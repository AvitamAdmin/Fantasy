package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWinningsForm extends BaseEntityForm{

    private  int userId;

    private int matchId;

    private int teamId;

    private double winningAmount;
}
