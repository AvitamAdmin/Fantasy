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
public class Contest extends CommonFields {
    private String contestTypeId;
    private Double totalPrice;
    private Double entryFee;
    private Long noOfMembers;
    private Map<String, Integer> rankPrice;
    private Long winPercentage;
    private Long maxTeams;
    private Double winningsAmount;
    private Integer profitPercentage;
    private String matchId;
}
