package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeamLineUpWsDto extends CommonWsDto {
    private List<TeamLineUpDto> teamLineUpDtoList;
}
