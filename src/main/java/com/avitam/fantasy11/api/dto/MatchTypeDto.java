package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.MatchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MatchTypeDto extends CommonDto{


    private List<MatchType> matchTypeList;
}
