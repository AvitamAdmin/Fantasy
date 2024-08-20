package com.avitam.fantasy11.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("General")
public class General extends BaseEntity{

    private String siteTitle;
    private String currency;
    private String timeZone;
    private String copyRight;
    private String comments;
    private String footerText;
    private String colorCode;
    private String emailVerify;
    private String emailNotify;
}
