package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.UserTeams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserTeamsDto extends CommonDto{

//    private UserTeams userTeams;
//    private List<UserTeams> userTeamsList;

    private String userId;
    private String matchId;
    private Set<String> players;
    private String teamName;
}
