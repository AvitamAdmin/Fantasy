package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.TeamLineUpDto;
import com.avitam.fantasy11.api.service.TeamLineUpService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MatchType;
import com.avitam.fantasy11.model.TeamLineup;
import com.avitam.fantasy11.model.TeamLineupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TeamLineUpServiceImpl implements TeamLineUpService {

    @Autowired
    private TeamLineupRepository teamLineupRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
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
        TeamLineup teamLineupOptional=teamLineupRepository.findByRecordId(recordId);
        //teamLineupOptional.ifPresent(teamLineup -> teamLineupRepository.save(teamLineup));
    }

    @Override
    public TeamLineUpDto handleEdit(TeamLineUpDto request) {
        TeamLineUpDto teamLineUpDto = new TeamLineUpDto();
        TeamLineup teamLineup = null;
        if(request.getRecordId()!=null){
            TeamLineup requestData = request.getTeamLineup();
            teamLineup = teamLineupRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, teamLineup);
        }
        else{
            teamLineup = request.getTeamLineup();
            teamLineup.setCreator(coreService.getCurrentUser().getUsername());
            teamLineup.setCreationTime(new Date());
            teamLineupRepository.save(teamLineup);
        }
        teamLineup.setLastModified(new Date());
        if(request.getRecordId()==null){
            teamLineup.setRecordId(String.valueOf(teamLineup.getId().getTimestamp()));
        }
        teamLineupRepository.save(teamLineup);
        teamLineUpDto.setTeamLineup(teamLineup);
        teamLineUpDto.setBaseUrl(ADMIN_TEAMLINEUP);
        return teamLineUpDto;
    }
}
