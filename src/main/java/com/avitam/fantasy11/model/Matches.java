package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Matches")

public class Matches extends BaseEntity{

    private String team1Id;
    private String contestId;
    private String team2Id;
    private String dateAndTime;
    private String startDateAndTime;
    private String endDateAndTime;
    private String event;
    private boolean matchStatus;
    private String tournamentId;
    private String sportTypeId;
    private String parentMainContestId;
    private String matchTypeId;
}