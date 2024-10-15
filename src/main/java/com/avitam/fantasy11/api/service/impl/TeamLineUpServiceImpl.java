package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.TeamLineUpDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.TeamLineUpService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.TeamLineup;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.TeamLineupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamLineUpServiceImpl implements TeamLineUpService {

    @Autowired
    private TeamLineupRepository teamLineupRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
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
            if(baseService.validateIdentifier(EntityConstants.TEAM_LINE_UP,request.getTeamLineup().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            teamLineup = request.getTeamLineup();
        }
        baseService.populateCommonData(teamLineup);
        teamLineupRepository.save(teamLineup);
        if(request.getRecordId()==null){
            teamLineup.setRecordId(String.valueOf(teamLineup.getId().getTimestamp()));
        }
        teamLineupRepository.save(teamLineup);
        teamLineUpDto.setTeamLineup(teamLineup);
        teamLineUpDto.setBaseUrl(ADMIN_TEAMLINEUP);
        return teamLineUpDto;
    }
}
