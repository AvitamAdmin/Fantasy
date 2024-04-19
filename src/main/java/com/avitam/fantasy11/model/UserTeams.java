package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document("UserTeams")
public class UserTeams extends  BaseEntity {

    private int userId;

    private int matchId;

    private String TeamName;

    private List<String>  players;

}
