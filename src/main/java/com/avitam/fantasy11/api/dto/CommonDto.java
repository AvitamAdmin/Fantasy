package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public class CommonDto extends BaseEntity {
        private String recordId;
        private  String token;
        private String email;
        private String otp;
        private String mobileNumber;
        private String username;

    }

