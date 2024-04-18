package model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("VerificationToken")
public class VerificationToken {

    private int id;
    private int userid;
    private int token;
    private int status;

}
