package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;

@Getter
@Setter
public class TournamentForm extends BaseEntityForm{

    private String tournamentName;
    private String sportId;
    private String dateAndTime;
    //private int teamId;
    //private int playerId;
    //private int lineupStatus;


}
