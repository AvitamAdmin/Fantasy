package com.avitam.fantasy11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("WebsiteSetting")
@Getter
@Setter
@NoArgsConstructor
public class WebsiteSetting extends CommonFields {
    private Binary logo;
    private Binary favicon;
    private String sportsApiUrl;
    private String sportsApiKey;
    private String mailId;
    private String mailPassword;
    private String smtpHost;
    private String smtpPort;
    private String otpKey;
    private String otpProvider;
    private String paymentKey;
    private String paymentProvider;
}
