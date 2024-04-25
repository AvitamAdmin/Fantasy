package com.avitam.fantasy11.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Document("VerificationToken")
public class VerificationToken extends BaseEntity{

    private int userid;
    private int status;

    private static final int EXPIRATION = 60 * 24;
    private String token;
    private User user;
    private Date expiryDate = calculateExpiryDate(EXPIRATION);

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}
