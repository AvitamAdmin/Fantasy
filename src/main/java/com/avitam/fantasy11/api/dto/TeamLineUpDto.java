package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.TeamLineup;
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

//    private TeamLineup teamLineup;
//    private List<TeamLineup> teamLineupList;
    private String teamId;

    private String playerId;

    private int lineupStatus;
}
