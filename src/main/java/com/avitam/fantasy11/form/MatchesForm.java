package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MatchesForm extends BaseEntityForm{

    private String teamId1;

    private String teamId2;

    private String dateAndTime;

    private String tournamentId;

    private String sportTypeId;

    private String contestId;

    private boolean matchStatus;

    private String matchTypeId;
}
