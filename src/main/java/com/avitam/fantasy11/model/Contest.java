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
