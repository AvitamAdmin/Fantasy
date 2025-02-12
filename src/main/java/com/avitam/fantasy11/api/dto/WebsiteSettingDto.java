package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebsiteSettingDto extends CommonDto {
    private MultipartFile logo;
    private MultipartFile favicon;
    private String sportsApiUrl;
    private String sportsApiKey;
    private String mailId;
    private String mailPassword;
    private String smtpHost;
    private String smtpPort;
    private String otpKey;
    private String otpProvider;
}
