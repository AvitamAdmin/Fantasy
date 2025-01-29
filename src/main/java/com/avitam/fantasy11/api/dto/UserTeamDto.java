package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserTeamDto extends CommonDto{

    private String playerId;
    private boolean captain;
    private boolean viceCaptain;
    private boolean impactPlayer;
}
