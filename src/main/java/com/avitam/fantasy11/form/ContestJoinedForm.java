package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContestJoinedForm extends BaseEntityForm {
    
    private int userid;

    private int matchId;

    private int teamId;

}
