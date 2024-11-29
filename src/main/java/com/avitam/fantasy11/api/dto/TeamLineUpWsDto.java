package com.avitam.fantasy11.api.dto;

import com.mongodb.annotations.Sealed;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeamLineUpWsDto extends CommonWsDto{
    private List<TeamLineUpDto> teamLineUpDtoList;
}
