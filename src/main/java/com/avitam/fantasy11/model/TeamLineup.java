package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("TeamLineup")
public class TeamLineup extends CommonFields {
    private String teamId;
    private String tournamentId;
    private String matchId;
    private String playerId;
    private boolean isPlaying;
    private boolean isImpact;
}
