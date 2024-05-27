package com.avitam.fantasy11.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserTeamsForm extends BaseEntityForm {

    private int userId;

    private int matchId;

    private String TeamName;

    private List<String> players;

}
