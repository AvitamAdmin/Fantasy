package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class UserTeamsForm extends BaseForm {

    private String userId;
    private String matchId;
    private String teamName;
    private List<String> players;

}
