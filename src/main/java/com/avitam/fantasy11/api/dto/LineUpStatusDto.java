package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class LineUpStatusDto extends CommonDto {

    private Boolean isPlaying;
    private Boolean isImpact;

}
