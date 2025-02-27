package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@ToString
public class CommonWsDto extends PaginationDto {
    private String baseUrl;
    private String message;
    private boolean success = true;
    private String redirectUrl;
    private String otp;


}

