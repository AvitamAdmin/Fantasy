package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("ContestJoined")

public class ContestJoined extends BaseEntity{

    private String userIds;

    private ObjectId matchId;

    private ObjectId teamId;

}
