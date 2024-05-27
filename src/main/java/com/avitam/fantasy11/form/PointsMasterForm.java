
package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PointsMasterForm extends BaseEntityForm{

    private int MatchTypeId;

    private float runs;

    private float wickets;

    private float halfCentury;

    private float century;

    private float thirtyPlus;

    private float threeWickets;

    private float fiveWickets;

    private float hatrickWickets;

    private float hatrickSixes;

    private float economy;


}
