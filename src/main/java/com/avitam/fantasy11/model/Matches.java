package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document("Matches")

public class Matches extends BaseEntity{

    private ObjectId teamId1;

    private ObjectId teamId2;

    private String dateAndTime;

    private boolean matchStatus;

    private ObjectId tournamentId;

    private ObjectId sportTypeId;

    private ObjectId contestId;

    private ObjectId matchTypeId;
}
