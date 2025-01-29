package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document("UserTeam")
public class UserTeam extends CommonFields {
    private String playerId;
    private boolean captain;
    private boolean viceCaptain;
    private boolean impactPlayer;
}
