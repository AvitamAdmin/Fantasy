package com.avitam.fantasy11.model;

import com.avitam.fantasy11.api.ContestCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Matches")
public class Matches extends CommonFields {
    private String team1Id;
    private String team2Id;
    private String matchTime;
    private String eventStatus;
    private String tournamentId;
    private String sportTypeId;
    private String matchTypeId;
    private String lineupStatus;
}