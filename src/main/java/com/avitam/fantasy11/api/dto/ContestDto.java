package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.Contest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContestDto extends CommonDto{
    private Contest contest;
    private List<Contest> contestList;
}
