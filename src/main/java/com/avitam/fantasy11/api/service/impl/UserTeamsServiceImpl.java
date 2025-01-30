package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserTeamWsDto;
import com.avitam.fantasy11.api.dto.UserTeamsDto;
import com.avitam.fantasy11.api.dto.UserTeamsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.UserTeamsService;
import com.avitam.fantasy11.model.UserTeams;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.UserTeamsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTeamsServiceImpl implements UserTeamsService {

    @Autowired
    private UserTeamsRepository userTeamsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;
    public static final String ADMIN_USERTEAM = "/admin/userTeam";

    @Override
    public UserTeams findByRecordId(String recordId) {
        return userTeamsRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        userTeamsRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        UserTeams userTeams = userTeamsRepository.findByRecordId(recordId);
        if (userTeams != null) {
            userTeamsRepository.save(userTeams);
        }
    }

//    @Override
//    public UserTeamWsDto handleEdit(UserTeamWsDto request) {
//        return null;
//    }

    @Override
    public UserTeamsWsDto handleEdit(UserTeamsWsDto request) {
        List<UserTeamsDto> userTeamDtos = request.getUserTeamsDtoList();
        List<UserTeams> userTeamsList = new ArrayList<>();
        UserTeams userTeams = null;

        for (UserTeamsDto userTeamsDto : userTeamDtos) {
            if (userTeamsDto.getRecordId() != null) {
                userTeams = userTeamsRepository.findByRecordId(userTeamsDto.getRecordId());
                modelMapper.map(userTeamsDto, userTeams);
                userTeamsRepository.save(userTeams);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.USER_TEAMS, userTeamsDto.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                userTeams = modelMapper.map(userTeamsDto, UserTeams.class);
            }
            baseService.populateCommonData(userTeams);
            userTeams.setStatus(true);
            userTeamsRepository.save(userTeams);
            if (userTeams.getRecordId() == null) {
                userTeams.setRecordId(String.valueOf(userTeams.getId().getTimestamp()));
            }
            userTeamsRepository.save(userTeams);
            request.setMessage("Data added Successfully");
            userTeamsList.add(userTeams);
        }
        request.setBaseUrl(ADMIN_USERTEAM);
        request.setUserTeamsDtoList(modelMapper.map(userTeamsList, List.class));
        return request;
    }
}
