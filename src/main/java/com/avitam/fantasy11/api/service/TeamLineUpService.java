package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.TeamLineUpDto;
import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.model.TeamLineup;

import java.util.Optional;

public interface TeamLineUpService {


    TeamLineup findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    TeamLineUpDto handleEdit(TeamLineUpDto request);
}
