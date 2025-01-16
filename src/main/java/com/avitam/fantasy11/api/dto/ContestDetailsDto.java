package com.avitam.fantasy11.api.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ContestDetailsDto extends CommonDto{
    private Double firstPrice;
    private  Float winningPercentage;
    private int maxEntries;
    private String winningId;
    private String contestId;


}
