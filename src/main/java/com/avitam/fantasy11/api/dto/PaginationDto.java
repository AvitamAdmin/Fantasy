package com.avitam.fantasy11.api.dto;


import org.springframework.data.domain.Sort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PaginationDto {

    private int page ;
    private int sizePerPage = 50;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private String sortField = "identifier";
}
