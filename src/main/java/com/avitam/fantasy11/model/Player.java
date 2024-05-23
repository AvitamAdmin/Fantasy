package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
@Getter
@Setter
public class Player  extends BaseEntity{

    private String name;
    private ObjectId teamId;
    private Binary playerImage;
    private ObjectId playerRole;
}
