package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document("TeamLineup")
public class    TeamLineup extends  CommonFields{

    private String matchId;

    private List<String> playerId;

    private int lineupStatus;

 //   private int impactPlayer;

}
