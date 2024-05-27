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
@Document("Address")

public class Address extends BaseEntity {

    private String mobileNumber;

    private String line_1;

    private String line_2;

    private String city;

    private String state;

    private String pinCode;


}
