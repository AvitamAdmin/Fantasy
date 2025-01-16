package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.TeamDto;
import com.avitam.fantasy11.api.dto.TeamWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.TeamService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.TeamRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    public static final String ADMIN_TEAM = "/admin/team";

    @Override
    public Team findByRecordId(String recordId) {
        return teamRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        teamRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Team team = teamRepository.findByRecordId(recordId);
        if (team != null) {
            teamRepository.save(team);
        }
    }

    @Override
    public TeamWsDto handleEdit(TeamWsDto request) {
        List<TeamDto> teamDtoList = request.getTeamDtoList();
        List<Team> teams = new ArrayList<>();
        Team team;
        for (TeamDto teamDto1 : teamDtoList) {
            if (teamDto1.getRecordId() != null) {
                team = teamRepository.findByRecordId(teamDto1.getRecordId());
                modelMapper.map(teamDto1, team);
                teamRepository.save(team);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.TEAM, teamDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                team = modelMapper.map(teamDto1, Team.class);

                if (teamDto1.getLogo() != null) {
                    try {
                        team.setLogo(new Binary(teamDto1.getLogo().getBytes()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                baseService.populateCommonData(team);
                team.setStatus(true);
                teamRepository.save(team);
                request.setMessage("Data added Successfully");
                if (team.getRecordId() == null) {
                    team.setRecordId(String.valueOf(team.getId().getTimestamp()));
                }
                teamRepository.save(team);
            }
            teams.add(team);
            request.setBaseUrl(ADMIN_TEAM);
        }
        request.setTeamDtoList(modelMapper.map(teams, List.class));
        return request;

    }
}
