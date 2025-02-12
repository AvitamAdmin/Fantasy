package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserTeamsDto extends CommonDto {
    private String userId;
    private String matchId;
    private String teamName;
    private String teamId;
    private String contestId;
}
