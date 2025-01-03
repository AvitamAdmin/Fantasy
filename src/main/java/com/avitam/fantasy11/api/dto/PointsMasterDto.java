package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PointsMasterDto extends CommonDto{

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
