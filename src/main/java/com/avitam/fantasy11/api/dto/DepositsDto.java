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
public class DepositsDto extends CommonDto{

    private Deposits deposits;
    private List<Deposits> depositsList;
}
