package com.avitam.fantasy11.model;

import jdk.jfr.Percentage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Document("Contest")
public class Contest extends BaseEntity{

    private String contestName;

    private Double totalPrice;

    private Double entryFee;

    private long noOfMembers;

    private Map<String,Double> rankPrice;

    private int winPercentage;

    private int maxTeams;

}
