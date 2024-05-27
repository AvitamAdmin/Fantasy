package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Player")
public class Player extends BaseEntity{

    private String playerName;
    private ObjectId teamId;
    private Binary playerImage;
    private ObjectId playerRoleId;

}
