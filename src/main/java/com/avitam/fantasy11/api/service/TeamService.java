package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.TeamWsDto;
import com.avitam.fantasy11.model.Team;

public interface TeamService {

    Team findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    TeamWsDto handleEdit(TeamWsDto request);
}
