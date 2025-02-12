package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContestJoinedDto extends CommonDto {

    private String userId;

    private String matchId;

    private String userTeamId;

    private String contestId;
}
