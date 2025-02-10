package com.avitam.fantasy11.api.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class SearchDto {
    private String label;
    private String attribute;
    private boolean dynamicAttr;
    private String dataType;
}
