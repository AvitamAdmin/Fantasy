package com.avitam.fantasy11.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public class CommonDto extends PaginationDto{
        private String baseUrl;
        private String recordId;
        private String message;
        private int  totalPages;
        private long totalRecords;
    }
