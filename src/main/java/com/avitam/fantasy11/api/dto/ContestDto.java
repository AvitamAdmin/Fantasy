package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Contest;
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
public class ContestDto extends CommonDto{
    private String mainContestId;
    private Double totalPrice;
    private Double entryFee;
    private int noOfMembers;
    private Map<String,Double> rankPrice;
    private int winPercentage;
    private int maxTeams;
    private double profit;
    private double totalAmount;
    private int profitPercentage;
    private double winningsAmount;
}
