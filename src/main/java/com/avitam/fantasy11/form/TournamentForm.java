package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TournamentForm extends BaseForm{

    private int teamId;
    private int playerId;
    private int lineupStatus;
    private Date dateAndTime;

}
