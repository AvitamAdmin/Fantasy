package com.avitam.fantasy11.form;

import com.avitam.fantasy11.model.Player;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserTeamsForm extends BaseForm {

    private String userId;
    private String matchId;
    private Set<String> players;

}
