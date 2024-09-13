package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.MainContest;
import java.util.List;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString

public class MainContestDto extends CommonDto {

        private MainContest mainContest;
        private List<MainContest> mainContestList;

    }
