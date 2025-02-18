package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LineUpPlayerDto extends CommonDto {
    private String playerId;
    private Boolean isPlaying;
    private Boolean isImpact;
}
