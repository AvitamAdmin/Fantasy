package com.avitam.fantasy11.api.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PaginationDto implements Serializable{
    private int page ;
    private int sizePerPage = 50;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private String sortField = "identifier";
    private int totalPages;
    private long totalRecords;

}
