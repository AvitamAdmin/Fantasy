package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.TeamLineUpDto;
import com.avitam.fantasy11.api.dto.TeamLineUpWsDto;
import com.avitam.fantasy11.model.TeamLineup;

public interface TeamLineUpService {


    TeamLineup findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    TeamLineUpWsDto handleEdit(TeamLineUpWsDto request);
}
