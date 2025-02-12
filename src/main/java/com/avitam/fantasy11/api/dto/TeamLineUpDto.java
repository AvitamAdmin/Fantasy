package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeamLineUpDto extends CommonDto {
    private String teamId;
    private String tournamentId;
    private String matchId;
    private String playerId;
    private boolean isPlaying;
    private boolean isImpact;
}
