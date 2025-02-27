package com.avitam.fantasy11.model;

import com.avitam.fantasy11.api.dto.RankPriceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Document("Contest")
public class Contest extends CommonFields {
    private String contestTypeId;
    private Double totalPrice;
    private Double entryFee;
    private Integer noOfMembers;
    private List<RankPriceDto> maxRankPrice;
    private Integer winPercentage;
    private Integer maxTeams;
    private Double winningsAmount;
    private Integer profitPercentage;
    private String matchId;
    private Boolean commonContest;
    private Double totalAmount;
    private Double profit;
    private Double currentBreakup; //only 200 spots filled
    private Double maxBreakup;// full spots filled


}
