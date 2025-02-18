package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Deposits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RankPriceDto {
    private String rank;
    private Integer winPercent;
    private Double amount;
}
