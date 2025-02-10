package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Document("Contest")
public class Contest extends CommonFields{

    private String mainContestId;
    private Double totalPrice;
    private Double entryFee;
    private int noOfMembers;
    private Map<String,Double> rankPrice;
    private int winPercentage;
    private int maxTeams;
    private double winningsAmount;
    private double totalAmount;
    private int profitPercentage;
    private double profit;
}
