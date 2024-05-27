package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document("tournament")

public class Tournament extends BaseEntity{

    private String tournamentName;

    private String dateAndTime;

    private ObjectId sportId;
}
