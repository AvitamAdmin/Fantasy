package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.UserTeamsDto;
import com.avitam.fantasy11.api.service.UserTeamsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.UserTeams;
import com.avitam.fantasy11.model.UserTeamsRepository;
import org.checkerframework.checker.units.qual.A;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserTeamsServiceImpl implements UserTeamsService {

    @Autowired
    private UserTeamsRepository userTeamsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    public static final String ADMIN_ADDRESS = "/admin/address";

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
        UserTeams userTeamsOptional=userTeamsRepository.findByRecordId(recordId);
        //userTeamsOptional.ifPresent(userTeams -> userTeamsRepository.save(userTeams));
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
            userTeams=request.getUserTeams();
            userTeams.setCreator(coreService.getCurrentUser().getUsername());
            userTeams.setCreationTime(new Date());
            userTeamsRepository.save(userTeams);
        }
        userTeams.setLastModified(new Date());
        if(request.getRecordId()==null){
            userTeams.setRecordId(String.valueOf(userTeams.getId().getTimestamp()));
        }
        userTeamsRepository.save(userTeams);
        userTeamsDto.setUserTeams(userTeams);
        userTeamsDto.setBaseUrl(ADMIN_ADDRESS);
        return userTeamsDto;
    }


}
