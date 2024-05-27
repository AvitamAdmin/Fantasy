package com.avitam.fantasy11.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Notification")
public class Notification extends BaseEntity{

    private String message;
    private String mobileNumber;
}
