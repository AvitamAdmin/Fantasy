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

    private String teamId1;
    private String teamId2;
    private String dateAndTime;
    private boolean matchStatus;
    private String tournamentId;
    private String sportTypeId;
    private String contestId;
    private String matchTypeId;
}