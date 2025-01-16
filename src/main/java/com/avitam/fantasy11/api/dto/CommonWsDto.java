package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class CommonWsDto extends PaginationDto{
    private String baseUrl;
    private String message;
    private boolean success = true;
    private  String token;
    private String userName;
    private String redirectUrl;
    private String otp;
    private MultipartFile image;
}

