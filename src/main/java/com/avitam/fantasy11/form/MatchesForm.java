package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MatchesForm extends BaseForm{

    private String team1Id;
    private String team2Id;
    private String contestId;
    private String startDateAndTime;
    private String endDateAndTime;
    private String event;
    private String tournamentId;
    private String sportTypeId;
    private String parentMainContestId;
    private boolean matchStatus;
    private String matchTypeId;

}
