package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("TeamLineup")
public class TeamLineup extends  BaseEntity{

    private int teamId;

    private int playerId;

    private int lineupStatus;

}
