package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.UserTeams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserTeamsDto extends CommonDto{

    private UserTeams userTeams;
    private List<UserTeams> userTeamsList;
}
