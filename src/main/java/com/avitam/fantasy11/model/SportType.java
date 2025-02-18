package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("SportType")
public class SportType extends CommonFields {
    private Binary logo;
    private Integer maxPlayers;
    private Integer section1MaxPlayers;
    private Integer section2MaxPlayers;
    private Integer section3MaxPlayers;
    private Integer section4MaxPlayers;
}
