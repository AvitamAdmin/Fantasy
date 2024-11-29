package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString@NoArgsConstructor
public class MatchesWsDto extends CommonWsDto{
    List<MatchesDto>matchesDtoList;
}
