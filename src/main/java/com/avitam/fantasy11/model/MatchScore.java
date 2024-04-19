package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("MatchScore")
public class MatchScore extends BaseEntity{

    private  int matchId;

    private int team1Score;

    private int team2Score;

    private float overs;
}
