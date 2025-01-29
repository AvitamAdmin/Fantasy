package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeamLineUpDto extends CommonDto{

    private String matchId;

    private List<String> playerId;

    private int lineupStatus;

  //  private int impactPlayer;
}
