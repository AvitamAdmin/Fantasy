package com.avitam.fantasy11.form;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamLineUpForm extends BaseEntityForm{

    private String teamId;

    private String playerId;

    private int lineupStatus;

}
