package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.MatchScore;
import com.avitam.fantasy11.model.Matches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MatchScoreDto extends CommonDto{

    private MatchScore matchScore;
    private List<MatchScore> matchScoreList;
}