package model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Address")

public class Address {

    private int id;

    private int userId;

    private String line_1;

    private String line_2;

    private String city;

    private String state;

    private String pinCode;


}
