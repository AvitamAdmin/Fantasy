package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeamLineUpDto extends CommonDto{

    private String matchId;

    private String playerId;

    private int lineupStatus;

    private int impactPlayer;
}
