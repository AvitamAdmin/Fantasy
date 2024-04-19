package model;


import com.avitam.fantasy11.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("VerificationToken")
public class VerificationToken extends BaseEntity {

    private int userid;
    private int token;
    private int status;

}
