package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Player")
public class Player extends CommonFields {
    private String dob;
    private String nationality;
    private String teamId;
    private String playerRoleId;
    private Binary playerImage;
}
