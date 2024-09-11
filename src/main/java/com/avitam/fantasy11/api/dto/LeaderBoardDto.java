package com.avitam.fantasy11.api.dto;

import com.avitam.fantasy11.model.LeaderBoard;
import com.avitam.fantasy11.model.Tournament;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LeaderBoardDto extends CommonDto{

    private LeaderBoard leaderBoard;
    private List<LeaderBoard> leaderBoardList;
}
