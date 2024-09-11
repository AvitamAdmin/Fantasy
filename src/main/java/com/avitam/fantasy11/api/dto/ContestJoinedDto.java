package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.ContestJoined;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContestJoinedDto extends CommonDto{

    private ContestJoined contestJoined;
    private List<ContestJoined> contestJoinedList;
}
