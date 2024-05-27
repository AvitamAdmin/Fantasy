package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ContestForm extends BaseEntityForm{

    private String contestName;

    private String name;

    private Double totalPrice;

    private Double entryFee;

    private long noOfMembers;

    private Map<String,Double> rankPrice;

    private int winPercent;

    private int maxTeams;

}
