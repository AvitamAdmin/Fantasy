package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("ContestJoined")

public class ContestJoined extends CommonFields{

    private String userId;

    private String matchId;

    private String userTeamId;

}
