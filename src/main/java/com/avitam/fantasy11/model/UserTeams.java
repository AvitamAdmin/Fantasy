package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document("UserTeams")
public class UserTeams extends  BaseEntity {

    private String userId;

    private String matchId;

    private String teamName;

    private List<String> players;

}
