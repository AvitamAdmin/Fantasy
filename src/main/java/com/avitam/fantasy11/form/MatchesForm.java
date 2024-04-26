package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MatchesForm {

    private String team1;

    private String team2;

    private Date dateAndTime;

    private int tournamentId;

    private int sportId;

    private int contestId;

    private int status;

    private int matchTypeId;
}
