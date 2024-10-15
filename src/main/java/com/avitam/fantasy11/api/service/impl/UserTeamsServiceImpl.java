package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserTeamsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.UserTeamsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.UserTeams;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.UserTeamsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTeamsServiceImpl implements UserTeamsService {

    @Autowired
    private UserTeamsRepository userTeamsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
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
        UserTeams userTeams=userTeamsRepository.findByRecordId(recordId);
        if(userTeams != null){
            userTeamsRepository.save(userTeams);
        }
    }

    @Override
    public UserTeamsDto handleEdit(UserTeamsDto request) {
        UserTeamsDto userTeamsDto = new UserTeamsDto();
        UserTeams userTeams = null;

        if(request.getRecordId()!=null){
            UserTeams requestData = request.getUserTeams();
            userTeams = userTeamsRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, userTeams);
        }
        else{
            if(baseService.validateIdentifier(EntityConstants.USER_TEAMS,request.getUserTeams().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            userTeams=request.getUserTeams();
        }
        baseService.populateCommonData(userTeams);
        userTeamsRepository.save(userTeams);
        if(request.getRecordId()==null){
            userTeams.setRecordId(String.valueOf(userTeams.getId().getTimestamp()));
        }
        userTeamsRepository.save(userTeams);
        userTeamsDto.setUserTeams(userTeams);
        userTeamsDto.setBaseUrl(ADMIN_USERTEAM);
        return userTeamsDto;
    }


}
