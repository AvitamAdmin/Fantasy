package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class CommonWsDto extends PaginationDto{
    private String baseUrl;
    private String recordId;
    private String message;
    private int  totalPages;
    private long totalRecords;
    private boolean success = true;
    private  String token;
    private String email;
    private String otp;
    private String mobileNumber;
    private String userName;
}

