package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.TeamDto;
import com.avitam.fantasy11.api.service.TeamService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.Team;
import com.avitam.fantasy11.model.TeamRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

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
        Team team=teamRepository.findByRecordId(recordId);
        if(team !=null) {
            teamRepository.save(team);
        }
    }

    @Override
    public TeamDto handleEdit(TeamDto request) {
        TeamDto teamDto = new TeamDto();
        Team team = null;
        if(request.getRecordId()!=null){
            Team requestData = request.getTeam();
            team = teamRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, team);
        }
        else {
            team = request.getTeam();
            team.setCreator(coreService.getCurrentUser().getUsername());
            team.setCreationTime(new Date());
            teamRepository.save(team);
            if (request.getLogo() != null && !request.getLogo().isEmpty()) {
                try {
                    team.setLogo(new Binary(request.getLogo().getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                    teamDto.setMessage("Error processing image file");
                    return teamDto;
                }
            }
        }
        team.setLastModified(new Date());
        if(request.getRecordId()==null){
            team.setRecordId(String.valueOf(team.getId().getTimestamp()));
        }
        teamRepository.save(team);
        teamDto.setTeam(team);
        teamDto.setBaseUrl(ADMIN_TEAM);
        return teamDto;

    }
}
