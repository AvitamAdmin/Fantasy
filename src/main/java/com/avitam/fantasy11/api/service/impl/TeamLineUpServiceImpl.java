package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.TeamLineUpDto;
import com.avitam.fantasy11.api.dto.TeamLineUpWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.TeamLineUpService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.TeamLineup;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.TeamLineupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamLineUpServiceImpl implements TeamLineUpService {

    @Autowired
    private TeamLineupRepository teamLineupRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;
    public static final String ADMIN_TEAMLINEUP = "/admin/teamLineup";

    @Override
    public TeamLineup findByRecordId(String recordId) {
        return teamLineupRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        teamLineupRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        TeamLineup teamLineup = teamLineupRepository.findByRecordId(recordId);
        if (teamLineup != null) {
            teamLineupRepository.save(teamLineup);
        }
    }

    @Override
    public TeamLineUpWsDto handleEdit(TeamLineUpWsDto request) {
        List<TeamLineUpDto> teamLineUpDtos = request.getTeamLineUpDtoList();
        List<TeamLineup> teamLineupList = new ArrayList<>();
        TeamLineup teamLineup = null;
        for (TeamLineUpDto teamLineUpDto1 : teamLineUpDtos) {
            if (request.getRecordId() != null) {
                TeamLineup requestData = modelMapper.map(teamLineUpDto1, TeamLineup.class);
                teamLineup = teamLineupRepository.findByRecordId(request.getRecordId());
                modelMapper.map(requestData, teamLineup);
            } else {
                if (baseService.validateIdentifier(EntityConstants.TEAM_LINE_UP, teamLineup.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                teamLineup = modelMapper.map(teamLineUpDto1, TeamLineup.class);
            }
            baseService.populateCommonData(teamLineup);
            teamLineupRepository.save(teamLineup);
            if (request.getRecordId() == null) {
                teamLineup.setRecordId(String.valueOf(teamLineup.getId().getTimestamp()));
            }
            teamLineupRepository.save(teamLineup);
            teamLineupList.add(teamLineup);
            request.setMessage("Data added Successfully");
            request.setBaseUrl(ADMIN_TEAMLINEUP);
        }
        request.setTeamLineUpDtoList(modelMapper.map(teamLineupList, List.class));
        return request;
    }
}
