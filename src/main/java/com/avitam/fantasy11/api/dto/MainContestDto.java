package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.MainContest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString

public class MainContestDto extends CommonDto {

//        private MainContest mainContest;
     // private List<MainContest> mainContestList;

    private String mainContestName;
    private String mainContestId;

    }
