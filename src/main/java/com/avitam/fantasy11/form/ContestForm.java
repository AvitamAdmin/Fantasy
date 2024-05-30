package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ContestForm extends BaseForm{

    private String contestName;

    private Double totalPrice;

    private Double entryFee;

    private long noOfMembers;

    private Map<String,Double> rankPrice;

    private int winPercentage;

    private int maxTeams;

}
