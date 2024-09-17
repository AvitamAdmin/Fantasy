package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.SportsApi;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SportAPIDto extends CommonDto {

    private SportsApi sportAPI;
    private List<SportsApi> sportsApiList;
}
