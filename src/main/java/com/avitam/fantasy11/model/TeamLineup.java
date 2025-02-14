package com.avitam.fantasy11.model;

import com.avitam.fantasy11.api.dto.LineUpPlayerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document("TeamLineup")
public class TeamLineup extends CommonFields {
    private String matchId;
    private Set<LineUpPlayerDto> players;
}
