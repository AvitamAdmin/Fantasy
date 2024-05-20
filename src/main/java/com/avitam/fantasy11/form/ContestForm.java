package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;
@Getter
@Setter
public class ContestForm extends BaseForm{

    private Double totalPrice;
    private Double entryFee;
    private long noOfMembers;
    private Map<String,Double> rankPrice;
    private int winPercent;
    private int maxTeams;

}
