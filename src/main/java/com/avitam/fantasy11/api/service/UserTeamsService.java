package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.UserTeamsWsDto;
import com.avitam.fantasy11.model.UserTeams;

public interface UserTeamsService {
    UserTeams findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    UserTeamsWsDto handleEdit(UserTeamsWsDto request);

    UserTeamsWsDto getUserTeamsDetails(UserTeamsWsDto request);
}
