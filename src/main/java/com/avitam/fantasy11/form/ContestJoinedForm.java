package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContestJoinedForm extends BaseEntityForm {
    
    private String userId;

    private String matchId;

    private String teamId;

}
