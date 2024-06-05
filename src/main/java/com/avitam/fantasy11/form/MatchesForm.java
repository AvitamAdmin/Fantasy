package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MatchesForm extends BaseForm{

    private String matchName;

    private String team1Id;

    private String team2Id;

    private String dateAndTime;

    private String tournamentId;

    private String sportTypeId;

    private String contestId;

    private boolean matchStatus;

    private String matchTypeId;
}
