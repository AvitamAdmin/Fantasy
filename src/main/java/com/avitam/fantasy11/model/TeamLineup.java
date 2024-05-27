package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("TeamLineup")
public class TeamLineup extends  BaseEntity{

    private ObjectId teamId;

    private ObjectId playerId;

    private int lineupStatus;

}
