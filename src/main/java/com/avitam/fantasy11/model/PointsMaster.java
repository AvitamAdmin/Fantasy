package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("PointsMaster")
public class PointsMaster extends CommonFields{

    private String matchTypeId;

    private float runs;

    private float wickets;

    private float halfCentury;

    private float century;

    private float thirtyPlus;

    private float threeWickets;

    private float fiveWickets;

    private float hattrickWickets;

    private float hattrickSixes;

    private float economy;


}
