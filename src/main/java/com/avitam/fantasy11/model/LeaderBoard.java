package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("LeaderBoard") //It will generate in MongoDB like LeaderBoard
public class LeaderBoard extends BaseEntity{

    private String userIds;

    private ObjectId tournamentId;

    private int matchesPlayed;

    private int rank;

    private  double bonusAmount;
}
