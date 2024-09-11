package com.avitam.fantasy11.api.dto;

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

public class TournamentDto extends CommonDto{

    private Tournament tournament;
    private List<Tournament> tournamentList;
}
