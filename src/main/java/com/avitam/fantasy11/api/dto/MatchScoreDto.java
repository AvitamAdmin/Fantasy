package com.avitam.fantasy11.api.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MatchScoreDto extends CommonDto {

    private String matchId;

    private int team1Score;

    private int team2Score;

    private float overs;

}
