package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("LeaderBoard")
public class LeaderBoard extends CommonFields{

    private String userId;

    private String tournamentId;

    private int matchesPlayed;

    private int rank;

    private  double bonusAmount;
}
