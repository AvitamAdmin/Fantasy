package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.TeamDto;
import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.model.Team;

import java.util.Optional;

public interface TeamService {

    Team findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    TeamDto handleEdit(TeamDto request);
}
