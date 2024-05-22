package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document("tournament")

public class Tournament extends BaseEntity{

    private String name;

    private Date dateAndTime;

    private int sportId;
}
