package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document("Matches")

public class Matches extends BaseEntity{

    private String team1;

    private String team2;

    private Date dateAndTime;

    private int tournamentId;

    private int sportId;

    private int contestId;

    private int status;

    private int matchTypeId;
}
