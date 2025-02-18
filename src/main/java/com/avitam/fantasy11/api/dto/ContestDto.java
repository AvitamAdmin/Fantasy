package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContestDto extends CommonDto {
    private String contestType;
    private Double totalPrice;
    private Double entryFee;
    private Integer noOfMembers;
    private List<RankPriceDto> maxRankPrice;
    private List<RankPriceDto> GuaranteedRankPrice;
    private Integer winPercentage;
    private Integer maxTeams;
    private Double winningsAmount;
    private Integer profitPercentage;
    private String matchId;
}
