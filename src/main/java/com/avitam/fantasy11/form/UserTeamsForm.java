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
    private String team1Players;
    private String team2Players;
    private List<String> players;

}
